package io.github.turskyi.expandablelistevents

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ExpandableListView
import android.widget.SimpleExpandableListAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        const val LOG_TAG = "myLogs"
    }

    var elvMain: ExpandableListView? = null
    var adapterHelper: AdapterHelper? = null
    var adapter: SimpleExpandableListAdapter? = null
    var tvInfo: TextView? = null

    /** Called when the activity is first created.  */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInfo = findViewById<View>(R.id.tvInfo) as TextView

        // Create an adapter
        adapterHelper = AdapterHelper(this)
        adapter = adapterHelper!!.adapter
        elvMain = findViewById<View>(R.id.elvMain) as ExpandableListView
        elvMain!!.setAdapter(adapter)

        // Clicking on an element
        elvMain!!.setOnChildClickListener { _, _, groupPosition, childPosition, id ->
            Log.d(
                LOG_TAG, "onChildClick groupPosition = " + groupPosition +
                        " childPosition = " + childPosition +
                        " id = " + id
            )
            tvInfo!!.text = adapterHelper!!.getGroupChildText(groupPosition, childPosition)
            false
        }

        // Clicking on a group
        elvMain!!.setOnGroupClickListener { _, _, groupPosition, id ->
            Log.d(
                LOG_TAG, "onGroupClick groupPosition = " + groupPosition +
                        " id = " + id
            )
            // Block further processing of the event for the group with position 1
            // (for demonstration purpose only)
            if (groupPosition == 1){
                Toast.makeText(this, "bocked on purpose", Toast.LENGTH_SHORT).show()
                true
            } else {
                false
            }
        }

        // Group collapse
        elvMain!!.setOnGroupCollapseListener { groupPosition ->
            Log.d(LOG_TAG, "onGroupCollapse groupPosition = $groupPosition")
            tvInfo!!.text = getString(R.string.collapse, adapterHelper!!.getGroupText(groupPosition))
        }

        // expanding the group
        elvMain!!.setOnGroupExpandListener { groupPosition ->
            Log.d(LOG_TAG, "onGroupExpand groupPosition = $groupPosition")
            tvInfo!!.text = getString(R.string.expanding, adapterHelper!!.getGroupText(groupPosition))
        }

        // Expand the group with position 2 without a click
        elvMain!!.expandGroup(2)
    }
}