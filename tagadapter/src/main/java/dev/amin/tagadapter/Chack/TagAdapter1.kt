package dev.amin.tagadapter.Chack

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import dev.amin.tagadapter.MeasureHelper
import dev.amin.tagadapter.MultipleSpanGridLayoutManager
import dev.amin.tagadapter.R
import dev.amin.tagadapter.Tag
import kotlinx.android.synthetic.main.row_tag.view.*
import java.util.ArrayList
import java.util.HashMap
import kotlin.properties.Delegates

class TagAdapter1(
        private var tagList: MutableList<Tag>,
        context: Context
) : RecyclerView.Adapter<TagAdapter1.Holder>() {


    var list = arrayListOf<Int>()
    var num: Int = 5

    /***
     * Measuring Helper which is used to measure each row of the recyclerView
     */
    private val measureHelper = MeasureHelper1(this, tagList.size)

    /***
     * Attached RecyclerView to the Adapter
     */
    private var recyclerView: RecyclerView? = null

    /***
     * First step is to get the width of the recyclerView then
     * Proceed to measuring the holders.
     *
     * Is RecyclerView done measuring.
     */
    private var ready = false

    /***
     * Determines when the measuring of all the cells is done.
     * If the newVal is true the adapter should be updated.
     */
    var measuringDone by Delegates.observable(false) { _, _, newVal ->
        if (newVal)
            update()
    }

    /***
     * Called to update the adapter with the new LayoutManager.
     */
    private fun update() {

        recyclerView ?: return

        recyclerView?.apply {

            visibility = View.VISIBLE

            // Get the list of sorted items from measureHelper
            tagList = measureHelper.getItems()

            // Get the list of sorted spans from measureHelper
            layoutManager = MultipleSpanGridLayoutManager(context, MeasureHelper.SPAN_COUNT, measureHelper.getSpans())
        }
    }

    /***
     * When recyclerView is attached measure the width and calculate the baseCell on measureHelper.
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        this.recyclerView = recyclerView.apply {

            visibility = View.INVISIBLE

            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {

                    // Prevent the multiple calls
                    recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    // Measure the BaseCell on MeasureHelper.
                    measureHelper.measureBaseCell(recyclerView.width)

                    ready = true

                    // NotifyDataSet because itemCount value needs to update.
                    notifyDataSetChanged()
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Holder(LayoutInflater.from(parent.context).inflate(R.layout.row_tag, parent, false))


    override fun getItemCount() = if (ready) tagList.size else 0

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val tag = tagList[position]

        // Determine if the MeasureHelper is done measuring if not holder should be measured.
        val shouldMeasure = measureHelper.shouldMeasure()

        holder.setData(tag, shouldMeasure)

        if (shouldMeasure)
            measureHelper.measure(holder, tag)


        try {

            // holder.changeBackground(0,holder.itemView.context)
            if (list.size > 0) {
                for (i in list) {
                    if (position == i) {
                        holder.changeBackground(num)
                    } else {
                        holder.changeBackgroundNormal()
                    }
                }
            } else {
                holder.changeBackgroundNormal()
            }
        } catch (e: Exception) {
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(tag: Tag, shouldMeasure: Boolean) {

            itemView.rowTitle.apply {

                text = tag.title

                /* set the height to normal, because in the measureHelper in order to fit
                    as much holders as possible we shrink the view to height of 1 */
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT


            }
            itemView.rowTitle.setBackgroundResource(R.drawable.title_cornered_normal)
            /* if the measuring is done set the width to fill the whole cell to avoid unwanted
                empty spaces between the cells */
            if (!shouldMeasure)
                itemView.rowTitle.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        }

        fun changeBackground(num: Int) {
            if (num == 0) {
                itemView.rowTitle.setBackgroundResource(R.drawable.title_cornered_color_orange)
            } else {
                itemView.rowTitle.setBackgroundResource(R.drawable.title_cornered_color_green)
            }
            itemView.rowTitle.setTextColor(Color.parseColor("#ffffff"))
        }

        fun changeBackgroundNormal() {
            itemView.rowTitle.setBackgroundResource(R.drawable.title_cornered_normal)
            itemView.rowTitle.setTextColor(Color.parseColor("#000000"))
        }
    }

    fun getClickPosition(list: ArrayList<Int>, num: Int) {
        //listPosition.put(position,position)
        this.num = num
        this.list = list
        notifyDataSetChanged()
    }
}