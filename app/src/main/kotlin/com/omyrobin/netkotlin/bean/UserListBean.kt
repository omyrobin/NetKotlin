package com.omyrobin.netkotlin.bean

/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/19 10:41 AM
 * @Description:
 */

class UserListBean {
    var courseId = 0
    var id = 0
    var name: String? = null
    var order = 0
    var parentChapterId = 0
    var userControlSetTop : Boolean? = false
    var visible = 0
    var children: List<*>? = null
}