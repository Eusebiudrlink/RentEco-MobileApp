package com.example.renteco_v1.rental_items.ui.vehicles

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.renteco_v1.R
import com.example.renteco_v1.Screen
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.rental_items.viewmodel.VehicleListViewModel
import com.example.renteco_v1.rental_items.data.model.AutoVehicle
import com.example.renteco_v1.rental_items.ui.renting.RentingScreen
import kotlinx.coroutines.launch



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleListView(
    onVehicleSelect: (Int) -> Unit,
    newNavSelected: (String) -> Unit,
    alreadyRenting:()->Unit,
    onLogOut: () -> Unit
) {
    val listViewModel = viewModel<VehicleListViewModel>(factory = VehicleListViewModel.Factory)
   // val sItems by listViewModel.uiState.collectAsStateWithLifecycle(initialValue = listOf())
    var sItems  by remember { mutableStateOf<List<AutoVehicle>>(emptyList()) }
    val uiState=listViewModel.uiState.collectAsState(listOf())
    sItems = uiState.value
    val expiredJWTState = listViewModel.expiredJWTState
    val curentlyRentingState=listViewModel.rentingState
    val itemsMenu=Menu.items


    if(expiredJWTState.value)
        onLogOut()//if jwt is expired user is logged out
    if(curentlyRentingState.value && Api.currentUser.inrent!=0) {
        Log.d("ViewModel", "User is already renting a vehicle: ${Api.currentUser.inrent}")
        alreadyRenting()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
        }

        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Text("Welcome ${Api.currentUser.full_name}", fontSize = 24.sp, modifier = Modifier.padding(8.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    itemsMenu.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                Text(text = item.title)
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                if (item.title != "Log out") {
                                    newNavSelected(item.route)
                                    selectedItemIndex = index
                                    scope.launch {
                                        drawerState.close()
                                    }
                                } else {
                                    onLogOut()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            badge = {
                                item.badgeCount?.let {
                                    Text(text = item.badgeCount.toString())
                                }
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }

                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "RentEco")
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                sItems=sItems.sortedBy { it.price }
                                Log.d("ListView","Sort ascending")
                                }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.sort_ascending),
                                    contentDescription = "SortAsc"
                                )
                            }
                            IconButton(onClick = {
                                sItems=sItems.sortedByDescending { it.price }
                                Log.d("ListView","Sort ascending")
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.sort_descending),
                                    contentDescription = "SortDesc"
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                        }


                    )
                },
                content = { values ->
                    Log.d(TAG, "Recompose ListView ${sItems.size}")
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(values)
                    ) {
                        items(sItems) { item ->
                            if(!item.rented)
                                VehicleDetail(vehicle = item, onVehicleSelect)
                        }
                    }
                }

            )
        }
    }
    LaunchedEffect(Unit){
        listViewModel.loadItems()
    }
}