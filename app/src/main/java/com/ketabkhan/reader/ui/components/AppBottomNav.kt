package com.ketabkhan.reader.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ketabkhan.reader.ui.theme.Primary
import com.ketabkhan.reader.ui.theme.SecondarySurface
import com.ketabkhan.reader.ui.theme.Surface
import com.ketabkhan.reader.ui.theme.TextSecondary
import com.ketabkhan.reader.ui.viewmodel.MainTab

@Composable
fun AppBottomNav(
    currentTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = Surface,
        contentColor = Primary,
        tonalElevation = 4.dp,
        modifier = modifier
            .height(64.dp)
            .testTag("app_bottom_nav")
    ) {
        NavigationBarItem(
            selected = currentTab == MainTab.LIBRARY,
            onClick = { onTabSelected(MainTab.LIBRARY) },
            icon = {
                Icon(
                    imageVector = if (currentTab == MainTab.LIBRARY) Icons.Filled.AutoStories else Icons.Outlined.AutoStories,
                    contentDescription = "کتابخانه من"
                )
            },
            label = {
                Text(
                    text = "کتابخانه",
                    fontSize = 12.sp,
                    fontWeight = if (currentTab == MainTab.LIBRARY) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Primary,
                selectedTextColor = Primary,
                indicatorColor = SecondarySurface,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary
            ),
            modifier = Modifier.testTag("nav_item_library")
        )

        NavigationBarItem(
            selected = currentTab == MainTab.CONVERT,
            onClick = { onTabSelected(MainTab.CONVERT) },
            icon = {
                Icon(
                    imageVector = if (currentTab == MainTab.CONVERT) Icons.Filled.AddCircle else Icons.Outlined.AddCircleOutline,
                    contentDescription = "ساخت کتاب"
                )
            },
            label = {
                Text(
                    text = "ساخت کتاب",
                    fontSize = 12.sp,
                    fontWeight = if (currentTab == MainTab.CONVERT) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Primary,
                selectedTextColor = Primary,
                indicatorColor = SecondarySurface,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary
            ),
            modifier = Modifier.testTag("nav_item_convert")
        )

        NavigationBarItem(
            selected = currentTab == MainTab.SETTINGS,
            onClick = { onTabSelected(MainTab.SETTINGS) },
            icon = {
                Icon(
                    imageVector = if (currentTab == MainTab.SETTINGS) Icons.Filled.Settings else Icons.Outlined.Settings,
                    contentDescription = "تنظیمات"
                )
            },
            label = {
                Text(
                    text = "تنظیمات",
                    fontSize = 12.sp,
                    fontWeight = if (currentTab == MainTab.SETTINGS) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Primary,
                selectedTextColor = Primary,
                indicatorColor = SecondarySurface,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary
            ),
            modifier = Modifier.testTag("nav_item_settings")
        )
    }
}
