package com.xzh.eyevideoclient.bean

/**
 * desc: 热门的tab
 */
data class TabInfoBean(val tabInfo: TabInfo) {
    data class TabInfo(val tabList: ArrayList<Tab>)
    data class Tab(val id: Long, val name: String, val apiUrl: String)
}
