package io.github.turskyi.expandablelistevents

import android.content.Context
import android.widget.SimpleExpandableListAdapter
import java.util.*

/**
 * kotlin version of the lesson from
 * https://startandroid.ru/ru/uroki/vse-uroki-spiskom/88-urok-46-sobytija-expandablelistview.html
 */
class AdapterHelper internal constructor(var context: Context) {
    companion object {
        const val ATTR_PHONE_NAME = "phoneName"
        const val ATTR_GROUP_NAME = "groupName"
    }

    // Names of companies (groups)
    private var groups = arrayOf("HTC", "Samsung", "LG")

    // Phone (item) names
    var phonesHTC = arrayOf("Sensation", "Desire", "Wildfire", "Hero")
    var phonesSamsung = arrayOf("Galaxy S II", "Galaxy Nexus", "Wave")
    var phonesLG = arrayOf("Optimus", "Optimus Link", "Optimus Black", "Optimus One")

    // Collection for groups
    private var groupData: ArrayList<Map<String, String?>>? = null

    // Collection for elements of one group.
    private var childDataItem: ArrayList<Map<String, String?>>? = null

    // General collection for item collections
    var childData: ArrayList<ArrayList<Map<String, String?>>>? = null

    // In the end, it will be: childData = ArrayList<childDataItem>
    // List of group and element attributes
    private var listMap: MutableMap<String, String?>? = null
    val adapter: SimpleExpandableListAdapter
        get() {

            // Fill in a collection of groups from an array with group names
            groupData = ArrayList()
            for (group in groups) {
                listMap = HashMap()
                // Fill in the list of attributes for each group
                listMap?.set(ATTR_GROUP_NAME, group) // name of the company
                listMap?.let { groupData!!.add(it) }
            }

            // List of group attributes to read only
            val groupFrom = arrayOf(ATTR_GROUP_NAME)
            // list of ID view-elements, in which the group attributes will be placed
            val groupTo = intArrayOf(android.R.id.text1)


            // create a collection for item collections
            childData = ArrayList()

            // create a collection of items for the first group
            childDataItem = ArrayList()
            // fill in the list of attributes for each element
            for (phone in phonesHTC) {
                listMap = HashMap()
                listMap?.set(ATTR_PHONE_NAME, phone) // название телефона
                listMap?.let { childDataItem!!.add(it) }
            }
            // add to the collection of collections
            childData!!.add(childDataItem!!)

            // create a collection of items for the second group
            childDataItem = ArrayList()
            for (phone in phonesSamsung) {
                listMap = HashMap()
                listMap?.set(ATTR_PHONE_NAME, phone)
                listMap?.let { childDataItem!!.add(it) }
            }
            childData!!.add(childDataItem!!)

            // create a collection of elements for the third group
            childDataItem = ArrayList()
            for (phone in phonesLG) {
                listMap = HashMap()
                listMap?.set(Companion.ATTR_PHONE_NAME, phone)
                listMap?.let { childDataItem!!.add(it) }
            }
            childData!!.add(childDataItem!!)

            // List of element attributes to read only
            val childFrom = arrayOf(ATTR_PHONE_NAME)
            // list of ID view-elements, where the attributes of the elements will be placed
            val childTo = intArrayOf(android.R.id.text1)
            return SimpleExpandableListAdapter(
                context,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo
            )
        }

    fun getGroupText(groupPos: Int): String {
        return (adapter.getGroup(groupPos) as Map<*, *>)[ATTR_GROUP_NAME] as String
    }

    private fun getChildText(groupPos: Int, childPos: Int): Any? {
        return (adapter.getChild(
            groupPos,
            childPos
        ) as Map<*, *>)[ATTR_PHONE_NAME]
    }

    fun getGroupChildText(groupPos: Int, childPos: Int): String {
        return getGroupText(groupPos) + " " + getChildText(groupPos, childPos)
    }
}