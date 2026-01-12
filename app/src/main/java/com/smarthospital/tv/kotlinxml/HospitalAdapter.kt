package com.smarthospital.tv.kotlinxml


//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.res.Resources
//import android.graphics.drawable.Drawable
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.cardview.widget.CardView
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.RecyclerView
//import coil.load
//import coil.request.CachePolicy
//import com.hcahealthcare.stb_android.R
//import com.hcahealthcare.stb_android.common.Util
//import com.hcahealthcare.stb_android.common.enums.ChartType
//import com.hcahealthcare.stb_android.common.enums.HospitalType
//import com.hcahealthcare.stb_android.common.interfaces.BindableRecyclerAdapter
//import com.hcahealthcare.stb_android.common.interfaces.STBGridAdapter
//import com.hcahealthcare.stb_android.customViews.VitalsChart
//import com.hcahealthcare.stb_android.dataModels.CareTeam
//import com.hcahealthcare.stb_android.dataModels.CareTeamED
//import com.hcahealthcare.stb_android.dataModels.GeneralInfo
//import com.hcahealthcare.stb_android.dataModels.ScheduledActivity
//import com.hcahealthcare.stb_android.dataModels.ScheduledActivityGroup
//import com.hcahealthcare.stb_android.dataModels.StaffHistory
//import com.hcahealthcare.stb_android.dataModels.Vital
//import java.text.ParseException
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.GregorianCalendar
//import java.util.Locale
//import java.util.TimeZone
//
//
//class HospitalAdapter(activity: Activity, val myHealthType: HospitalType): RecyclerView.Adapter<HospitalAdapter.HospitalDashboardItemHolder>(),
//    BindableRecyclerAdapter<List<Any>>, STBGridAdapter {
//
//    private val layoutInflater: LayoutInflater = LayoutInflater.from(activity)
//
//    private var cardWidthUnfocused = 0
//    private var cardHeightUnfocused = 0
//    private var cardWidthFocused = 0
//    private var cardHeightFocused = 0
//
//    private var chartWidthUnfocused = 0
//    private var chartHeightUnfocused = 0
//    private var chartWidthFocused = 0
//    private var chartHeightFocused = 0
//
//    private var generalInfoCardWidthFocused = 0
//    private var generalInfoCardWidthUnFocused = 0
//
//    private var cardElevationFocused = 0.0F
//
//    private var focusedDrawable : Drawable? = null
//    private var unfocusedDrawable : Drawable? = null
//
//    private var recyclerHorizontalBuffer = 0
//
//    var sasToken = ""
//
//    var selectedIndex = -1
//
//    var currentHealthType : HospitalType? = null
//
//    var items : List<Any>? = null
//    var resources : Resources = activity.resources
//
//    init {
//        focusedDrawable = ContextCompat.getDrawable(activity, R.drawable.my_health_bg_focused_radial_gradient)
//        unfocusedDrawable = ContextCompat.getDrawable(activity, R.drawable.my_health_bg_unfocused_radial_gradient)
//
//        cardWidthUnfocused = resources.getDimension(myHealthType.cardWidthUnfocusedId).toInt()
//        cardHeightUnfocused = resources.getDimension(myHealthType.cardHeightUnfocusedId).toInt()
//        cardWidthFocused = resources.getDimension(myHealthType.cardWidthFocusedId).toInt()
//        cardHeightFocused = resources.getDimension(myHealthType.cardHeightFocusedId).toInt()
//
//        generalInfoCardWidthFocused = resources.getDimension(R.dimen.general_info_card_width_focused).toInt()
//        generalInfoCardWidthUnFocused = resources.getDimension(R.dimen.general_info_card_width_unfocused).toInt()
//
//        chartWidthUnfocused = resources.getDimension(R.dimen.chart_width_unfocused).toInt()
//        chartHeightUnfocused = resources.getDimension(R.dimen.chart_height_unfocused).toInt()
//        chartWidthFocused = resources.getDimension(R.dimen.chart_width_focused).toInt()
//        chartHeightFocused = resources.getDimension(R.dimen.chart_height_focused).toInt()
//
//        cardElevationFocused = resources.getDimension(R.dimen.my_health_card_elevation)
//
//        recyclerHorizontalBuffer = resources.getDimension(R.dimen.health_recycler_horizontal_buffer).toInt()
//    }
//
//
//    @SuppressLint("NotifyDataSetChanged")
//    override fun setData(data: List<Any>?) {
//        this.items = data
//        notifyDataSetChanged()
//    }
//
//    fun setToken(token: String){
//        sasToken = token
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalDashboardItemHolder {
//        val view = layoutInflater.inflate(myHealthType.layoutResourceId, parent, false)
//        return HospitalDashboardItemHolder(view, myHealthType)
//    }
//
//
//    override fun onBindViewHolder(holder: HospitalDashboardItemHolder, position: Int) {
//        holder.itemView.findViewById<CardView>(R.id.myHealthCardView).tag = position
//        items?.get(position)?.let { item ->
//            holder.bind(item, position, sasToken)
//            val buffer = resources.getDimension(myHealthType.horizontalBufferId).toInt()
//            Util.adjustHorizontalGridViewLeftRightBuffer(buffer, position, itemCount,
//                holder.myHealthLinearLayout)
//        }
//    }
//
//
//    override fun getItemCount(): Int {
//        return items?.size ?: 0
//    }
//
//
//    class HospitalDashboardItemHolder(
//        itemView: View,
//        private var myHealthType: HospitalType
//    ) : RecyclerView.ViewHolder(itemView) {
//
//        private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//        val myHealthOverlayView : View? = itemView.findViewById(R.id.myHealthFocusedOverlayView)
//        val myHealthLinearLayout : LinearLayout = itemView.findViewById(R.id.myHealthLinearLayout)
//        val myHealthConstraintLayout : ConstraintLayout = itemView.findViewById(R.id.myHealthConstraintView)
//        val myHealthImageView : ImageView? = itemView.findViewById(R.id.myHealthImageView)
//        private val myHealthIconView : ImageView? = itemView.findViewById(R.id.myHealthIconView)
//        val myHealthCardView : CardView = itemView.findViewById(R.id.myHealthCardView)
//        val myHealthChart : VitalsChart? = itemView.findViewById(R.id.myHealthChart)
//
//        private val generalInfoOuterCardWidth = itemView.resources.getDimension(R.dimen.general_info_outer_card_width).toInt()
//        private val generalInfoOuterCardNormalWidth = itemView.resources.getDimension(R.dimen.schedule_outer_card_width).toInt()
//        private val generalInfoCardWidth = itemView.resources.getDimension(R.dimen.general_info_card_width_unfocused).toInt()
//        private val generalInfoCardNormalWidth = itemView.resources.getDimension(R.dimen.schedule_card_width_unfocused).toInt()
//
//        fun bind(item: Any, position: Int, sasToken: String) {
//            val textView1 = itemView.findViewById<TextView>(R.id.myHealthTextView1)
//            val textView2 = itemView.findViewById<TextView>(R.id.myHealthTextView2)
//            val textView3 = itemView.findViewById<TextView>(R.id.myHealthTextView3)
//            val wonkBakerImageView= itemView.findViewById<ImageView>(R.id.wongBakerImage)
//            when (myHealthType) {
//                HospitalType.VITALS -> {
//                    val vitalSign = item as Vital
//                    val chartType = ChartType.chartTypeForAPIName(vitalSign.displayName)
//                    myHealthIconView?.setImageResource(chartType.iconId)
//                    myHealthChart?.vitalSign = vitalSign
//                    var vitalsText = ""
//                    vitalsText = if (!vitalSign.unit.isNullOrEmpty()) {
//                        "${vitalSign.displayName} (${vitalSign.unit})"
//                    } else {
//                        vitalSign.displayName
//                    }
//                    textView1.text = vitalsText
//                }
//                HospitalType.SCHEDULE -> {
//                    textView2.setLines(3)
//                    val scheduledActivity = item as ScheduledActivity
//                    textView1.text = scheduledActivity.title
//                    textView2.text = scheduledActivity.description
//                }
//                HospitalType.STAFFHISTORY -> {
//                    val staffHistoryItem = item as StaffHistory
//                    Util.setCaregiverImageResource(myHealthImageView, staffHistoryItem.staffType)
//                    val person = when(staffHistoryItem.staffType) {
//                        "Doctor" ->  "Dr. ${staffHistoryItem.firstName}"
//                        else -> staffHistoryItem.firstName
//                    }
//                    textView1.text = person
//                    textView2.text = staffHistoryItem.staffType
//                    try {
//                        val date = dateFormat.parse(staffHistoryItem.enteredDateTime.substring(0, 19))
//                        val calendar = GregorianCalendar()
//                        date?.let { calendar.time = it }
//                        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
//                        var hour = calendar.get(Calendar.HOUR)
//                        val minute = calendar.get(Calendar.MINUTE)
//                        val paddedMinute = if (minute < 10) "0$minute" else "$minute"
//                        val amPm = if (hourOfDay > 11) " PM" else " AM"
//                        if (hour == 0) {
//                            hour = 12
//                        }
//                        val timeString = "$hour:$paddedMinute$amPm"
//                        textView3.text = timeString
//                    } catch (e: ParseException) {
//                        Log.e("HospitalDashboardItemHolder", "parse exception for date!")
//                    }
//                }
//                HospitalType.CARETEAM -> {
//                    val careProvider = item as CareTeam
//                    if (careProvider.slot == null && (careProvider.clinicalRole == "AttendingPhysician" || careProvider.clinicalRole == "AdmittingPhysician")) {
//                        textView2.text = "Provider"
//                    } else {
//                        textView2.text = getCareProviderType(careProvider.slot)
//                    }
//                    Util.setCaregiverImageResource(myHealthImageView, careProvider.slot)
//                    textView1.text = when(careProvider.clinicalRole) {
//                        "AttendingPhysician" ->  "Dr. ${careProvider.lastName}"
//                        "AdmittingPhysician" ->  "Dr. ${careProvider.lastName}"
//                        "CTA.ClinicalRoles.LactationConsultant" -> careProvider.firstName
//                        "CTA.ClinicalRoles.CaseManagement" -> careProvider.firstName
//                        "CTA.ClinicalRoles.PhysicalTherapy" -> careProvider.firstName
//                        else -> "${careProvider.firstName} ${careProvider.lastName}"
//                    }
//                }
//                HospitalType.ORDERS -> {
//                    textView2.setLines(3)
//                    val scheduledActivity = item as ScheduledActivityGroup
//                    textView1.text = scheduledActivity.groupName
//                    textView2.text = scheduledActivity.orderCountRatio
//                }
//
//                HospitalType.CARETEAMED ->  {
//                    val careProvider = item as CareTeamED
//                    Util.setCaregiverImageResource(myHealthImageView, careProvider.role)
//                    textView2.text = careProvider.role
//                    textView1.text = if (careProvider.role == "Provider" || careProvider.role == "Attending Provider") {
//                        "Dr. ${careProvider.lastName}"
//                    } else {
//                        careProvider.firstName
//                    }
//                }
//
//                HospitalType.GENERALINFO -> {
//                    val generalInfo = item as GeneralInfo
//                    setPatientInfoCardWidth(generalInfo.title, myHealthLinearLayout, myHealthCardView)
//                    setNumberOfLines(generalInfo.title, textView2)
//
//                    textView1.text = generalInfo.title
//                    textView2.text = when (generalInfo.title) {
//                        "Arrival" -> {
//                            convertToLocalTime(generalInfo.values?.getOrNull(0) )
//                        }
//                        else -> generalInfo.values?.getOrNull(0)
//
//                    }
//                    if (generalInfo.title == "Pain Level") {
//                        wonkBakerImageView.visibility = View.VISIBLE
//                        val imageUrl = generalInfo.values?.getOrNull(1).toString() + sasToken
//                        wonkBakerImageView.load(imageUrl){
//                            memoryCacheKey(imageUrl)
//                            memoryCachePolicy(CachePolicy.ENABLED)
//                            diskCachePolicy(CachePolicy.ENABLED)
//                            networkCachePolicy(CachePolicy.ENABLED)
//                        }
//                        textView3.text = generalInfo.values?.getOrNull(2).toString()
//                    }
//                }
//            }
//        }
//
//        private fun setNumberOfLines(title: String, textView2: TextView) {
//            when (title) {
//                "Pain Level" -> {
//                    textView2.setLines(1)
//                }
//                else -> {
//                    textView2.setLines(3)
//                }
//            }
//        }
//
//        private fun convertUnicodeToEmoji(value: String): String {
//            val input = value.replace("u+", "0x")
//            val x = Integer.decode(input)
//            return StringBuilder().appendCodePoint(x).toString()
//        }
//
//        private fun convertToLocalTime(value: String?): String? {
//            val dateFormat = SimpleDateFormat("MM/dd/yyyy h:mm:ss a")
//            val localDateFormat = SimpleDateFormat("MMM d, yyyy h:mm a")
//            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
//            val date = value?.let { dateFormat.parse(it) }
//            return date?.let { localDateFormat.format(it) }
//        }
//
//        private fun setPatientInfoCardWidth(
//            title: String,
//            myHealthLinearLayout: LinearLayout,
//            myHealthCardView: CardView
//        ) {
//            val myHealthLinearLayoutParams = myHealthLinearLayout.layoutParams
//            val myHealthCardViewParams = myHealthCardView.layoutParams
//            when (title) {
//                "Arrival" -> {
//                    myHealthLinearLayoutParams.width = generalInfoOuterCardNormalWidth
//                    myHealthCardViewParams .width = generalInfoCardNormalWidth
//                }
//                else -> {
//                    myHealthLinearLayoutParams.width = generalInfoOuterCardWidth
//                    myHealthCardViewParams.width = generalInfoCardWidth
//                }
//            }
//            myHealthLinearLayout.layoutParams = myHealthLinearLayoutParams
//        }
//
//        private fun getCareProviderType(slot: String?): String {
//            return when(slot) {
//                "CTA.ClinicalRoles.RN.PCT" -> "Tech"
//                "CTA.ClinicalRoles.RN.CRN" -> "Nurse Leader"
//                "CTA.ClinicalRoles.RN.1N" -> "Nurse"
//                "CTA.ClinicalRoles.LactationConsultant" -> "Lactation Consult"
//                "CTA.ClinicalRoles.CaseManagement" -> "Case Manager"
//                "CTA.ClinicalRoles.PhysicalTherapy" -> "Physical Therapist"
//                else -> {"Tech"}
//            }
//
//        }
//
//    }
//
//
//    override fun handleFocusChange(hasFocus: Boolean, itemIndex: Int, viewHolder: RecyclerView.ViewHolder) {
//        val holder = viewHolder as HospitalDashboardItemHolder
//        val layoutParams = holder.myHealthCardView.layoutParams
//        if (myHealthType != HospitalType.GENERALINFO) {
//            val cardWidth = if (hasFocus) cardWidthFocused else cardWidthUnfocused
//            val cardHeight = if (hasFocus) cardHeightFocused else cardHeightUnfocused
//            layoutParams.width = cardWidth
//            layoutParams.height = cardHeight
//            holder.myHealthCardView.layoutParams = layoutParams
//        }
//
//        holder.myHealthOverlayView?.visibility = if (!hasFocus) View.VISIBLE else View.GONE
//        holder.myHealthConstraintLayout.background = if (!hasFocus) unfocusedDrawable else focusedDrawable
//        holder.myHealthCardView.cardElevation = if (!hasFocus) 0.0F else cardElevationFocused
//        selectedIndex = if (!hasFocus) -1 else holder.myHealthCardView.tag as Int
//        currentHealthType = if (!hasFocus) null else myHealthType
//
//        when(myHealthType) {
//            HospitalType.CARETEAM, HospitalType.STAFFHISTORY, HospitalType.CARETEAMED -> {
//                val imageLayoutParams = holder.myHealthImageView?.layoutParams ?: return
//                val cardHeight = if (hasFocus) cardHeightFocused else cardHeightUnfocused
//                imageLayoutParams.width = cardHeight
//                imageLayoutParams.height = cardHeight
//                holder.myHealthImageView.layoutParams = imageLayoutParams
//            }
//            HospitalType.VITALS -> {
//                val chartLayoutParams = holder.myHealthChart?.layoutParams ?: return
//                chartLayoutParams.width = if (hasFocus) chartWidthFocused else chartWidthUnfocused
//                chartLayoutParams.height = if (hasFocus) chartHeightFocused else chartHeightUnfocused
//                holder.myHealthChart.layoutParams = chartLayoutParams
//                holder.myHealthChart.hasFocus = hasFocus
//            }
//            HospitalType.SCHEDULE, HospitalType.ORDERS -> {
//                val textView2 = holder.itemView.findViewById<TextView>(R.id.myHealthTextView2)
//                textView2?.setLines(if (!hasFocus) 3 else 4)
//            }
//            HospitalType.GENERALINFO -> {
//                val textView2 = holder.itemView.findViewById<TextView>(R.id.myHealthTextView2)
//                val item = items?.get(itemIndex) as GeneralInfo
//                when (item.title) {
//                    "Pain Level" -> {
//                        textView2.setLines(1)
//                    }
//                    else -> {
//                        textView2?.setLines(if (!hasFocus) 3 else 4)
//                    }
//                }
//                val cardWidth = when (item.title) {
//                    "Arrival" -> {
//                        if (hasFocus) cardWidthFocused else cardWidthUnfocused
//                    }
//                    else -> {
//                        if (hasFocus) generalInfoCardWidthFocused else generalInfoCardWidthUnFocused
//                    }
//                }
//                val cardHeight = if (hasFocus) cardHeightFocused else cardHeightUnfocused
//                layoutParams.width = cardWidth
//                layoutParams.height = cardHeight
//                holder.myHealthCardView.layoutParams = layoutParams
//            }
//        }
//    }
//
//}