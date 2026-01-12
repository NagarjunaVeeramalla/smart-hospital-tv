package com.smarthospital.tv.kotlinxml


//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.fragment.app.FragmentActivity
//import androidx.fragment.app.activityViewModels
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.MutableLiveData
//import androidx.navigation.NavController
//import androidx.navigation.Navigation
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.engine.DiskCacheStrategy
//import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
//import com.google.gson.Gson
//import com.hcahealthcare.stb_android.R
//import com.hcahealthcare.stb_android.analytics.AnalyticsConstants.EventName.Companion.NOT_NOW_SELECT
//import com.hcahealthcare.stb_android.analytics.AnalyticsConstants.EventName.Companion.SHARE_FEEDBACK_ON_MY_HEALTH_SELECT
//import com.hcahealthcare.stb_android.common.AppPreferences
//import com.hcahealthcare.stb_android.common.TestData
//import com.hcahealthcare.stb_android.common.Util
//import com.hcahealthcare.stb_android.common.Util.Companion.generateLogContent
//import com.hcahealthcare.stb_android.common.enums.HospitalType
//import com.hcahealthcare.stb_android.common.enums.VisionMode
//import com.hcahealthcare.stb_android.common.interfaces.STBGridAdapter
//import com.hcahealthcare.stb_android.common.postDelayed
//import com.hcahealthcare.stb_android.common.sasTokenKey
//import com.hcahealthcare.stb_android.data.NetworkResult
//import com.hcahealthcare.stb_android.dataModels.GeneralInfo
//import com.hcahealthcare.stb_android.dataModels.HospitalDataModel
//import com.hcahealthcare.stb_android.dataModels.ScheduledActivity
//import com.hcahealthcare.stb_android.dataModels.Vital
//import com.hcahealthcare.stb_android.databinding.FragmentHospitalBinding
//import com.hcahealthcare.stb_android.fragments.STBGridFragment
//import com.hcahealthcare.stb_android.network.AuthHeaderManager
//import com.hcahealthcare.stb_android.recyclerAdapters.HospitalAdapter
//import com.hcahealthcare.stb_android.util.getDeviceHeight
//import com.hcahealthcare.stb_android.viewModel.MainViewModel
//import com.hcahealthcare.stb_android.viewModel.HospitalDashboardViewModel
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.serialization.ExperimentalSerializationApi
//import kotlinx.serialization.json.Json
//import org.json.JSONObject
//import javax.inject.Inject
//
//@AndroidEntryPoint
//class HospitalFragment : STBGridFragment() {
//
//    private var navController: NavController? = null
//
//    private lateinit var binding: FragmentHospitalBinding
//
//    var vitalsAdapter: HospitalAdapter? = null
//    private var scheduleAdapter: HospitalAdapter? = null
//    private var careTeamAdapter: HospitalAdapter? = null
//    private var staffHistoryAdapter: HospitalAdapter? = null
//
//    private var myHealthOneRow = -1
//    private var myHealthOneRowIsSelected = false
//
//    private val viewModel by viewModels<HospitalDashboardViewModel>()
//    private val mainViewModel by activityViewModels<MainViewModel>()
//
//    private var isShareFeedbackDialogShown = false
//    private var shareFeedbackDialogUpPressedState by mutableStateOf(false)
//    private var shareFeedbackDialogDownPressedState by mutableStateOf(false)
//    private var shareFeedbackDialogCenterPressedState by mutableStateOf(false)
//
//    @Inject
//    lateinit var authHeaderManager: AuthHeaderManager
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        scrollView = binding.myHealthScrollView
//        populateHospitalOneBanner()
//        getHospitalDetails()
//
//        //supplyPlaceholderChartInfoIfNecessary(mainViewModel.patientHospital)
//        mainViewModel.patientHospital.observe(viewLifecycleOwner) { myHealthResponse ->
//            if (!Util.hasAnyHospitalInfo(myHealthResponse) && myHealthResponse != null) {
//                mainViewModel.patientHospital.postValue(null)
//                navController?.popBackStack()
//            } else {
//                //supplyPlaceholderChartInfoIfNecessary(mainViewModel.patientHospital) // if data is updated after initial load
//                postDelayed(20L) {
//                    populateGridViews()
//                }
//            }
//        }
//
//        if (navController == null) {
//            navController = Navigation.findNavController(view)
//        }
//
//        val fragmentActivity = activity as FragmentActivity
//
//
//        if (mainViewModel.visionLayout == VisionMode.EdOnly) {
//            setGeneralInfoGridHeight()
//            binding.scheduleTextView.text = HospitalType.ORDERS.typeName
//            vitalsAdapter = HospitalAdapter(fragmentActivity, HospitalType.GENERALINFO)
//            scheduleAdapter = HospitalAdapter(fragmentActivity, HospitalType.ORDERS)
//            careTeamAdapter = HospitalAdapter(fragmentActivity, HospitalType.CARETEAMED)
//        } else {
//            binding.scheduleTextView.text = HospitalType.SCHEDULE.typeName
//            vitalsAdapter = HospitalAdapter(fragmentActivity, HospitalType.VITALS)
//            scheduleAdapter = HospitalAdapter(fragmentActivity, HospitalType.SCHEDULE)
//            careTeamAdapter = HospitalAdapter(fragmentActivity, HospitalType.CARETEAM)
//        }
//
//        staffHistoryAdapter = HospitalAdapter(fragmentActivity, HospitalType.STAFFHISTORY)
//
//        binding.vitalsOrGeneralInfoGridView.adapter = vitalsAdapter
//        binding.scheduleOrOrderGridView.adapter = scheduleAdapter
//        binding.careTeamGridView.adapter = careTeamAdapter
//        binding.staffHistoryGridView.adapter = staffHistoryAdapter
//
//        // To prevent unexpected scrolling when grid views are set to visible or
//        // gone when looking at the screen and a reload occurs because of new data:
//        binding.vitalsOrGeneralInfoGridView.focusable = View.NOT_FOCUSABLE
//        binding.scheduleOrOrderGridView.focusable = View.NOT_FOCUSABLE
//        binding.careTeamGridView.focusable = View.NOT_FOCUSABLE
//        binding.staffHistoryGridView.focusable = View.NOT_FOCUSABLE
//
//        populateGridViews()
//
//    }
//
//    private fun setGeneralInfoGridHeight() {
//        val gridViewParams = binding.vitalsOrGeneralInfoGridView.layoutParams
//        gridViewParams.height = resources.getDimension(R.dimen.schedule_grid_height).toInt()
//        binding.vitalsOrGeneralInfoGridView.layoutParams = gridViewParams
//    }
//
//    private fun getHospitalDetails() {
//        with(viewModel) {
//            accountNumber = mainViewModel.patient.value?.id
//            fetchHospitalDetails()
//            myHealthResponse.observe(viewLifecycleOwner) {
//                when (it) {
//                    is NetworkResult.Loading -> {}
//
//                    is NetworkResult.Success -> {
//                        Util.logLongString(JSONObject(Gson().toJson(it.data)).toString(4))
//                        it.data.layout = mainViewModel.visionLayout.modeName
//                        mainViewModel.patientHospital.postValue(it.data)
//                        vitalsAdapter?.setToken(authHeaderManager.safeGetRequestHeaders()[sasTokenKey].toString())
//                        if (mainViewModel.visionLayout == VisionMode.EdOnly) {
//                            val modifiedList = removeEmptyValues(it.data.generalInfo)
//                            vitalsAdapter?.setData(modifiedList)
//                            scheduleAdapter?.setData(it.data.scheduledActivityGroupCounts)
//                            careTeamAdapter?.setData(it.data.careTeamED)
//                        } else {
//                            vitalsAdapter?.setData(it.data.vitalSigns)
//                            scheduleAdapter?.setData(it.data.scheduledActivities)
//                            careTeamAdapter?.setData(it.data.careTeam)
//                        }
//                        showShareFeedbackDialog(it.data.isFeedbackComplete)
//                    }
//
//                    is NetworkResult.Failure -> {
//                        Util.logLongString(it.errorResponseBody)
//                        mainViewModel.patientHospital.postValue(HospitalDataModel())
//                        if (mainViewModel.isLogLevelCritical) {
//                            val logContent = generateLogContent(
//                                requireContext(),
//                                it.errorResponseBody
//                            )
//                            mainViewModel.uploadLogBySerial(logContent)
//                        }
//                    }
//                }
//
//            }
//        }
//    }
//
//    private fun removeEmptyValues(generalInfo: List<GeneralInfo>?): MutableList<GeneralInfo> {
//        val modifiedList = generalInfo as MutableList<GeneralInfo>
//        for (item in generalInfo) {
//            if (item.values.isNullOrEmpty()) {
//                modifiedList.remove(item)
//            }
//        }
//        return modifiedList
//    }
//
//    private fun populateHospitalOneBanner() {
//        val mhoImageName = mainViewModel.deviceLocation.value?.config?.myHealthBannerUrl ?: return
//        if (mhoImageName.isBlank()) {
//            return
//        }
//
//        Glide.with(requireContext())
//            .load(mhoImageName + authHeaderManager.safeGetRequestHeaders()[sasTokenKey].toString())
//            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//            .transition(DrawableTransitionOptions.withCrossFade())
//            .into(binding.mhoBannerImageView)
//    }
//
//
//    private fun populateGridViews() {
//        myHealthOneRow = -1
//        gridViews.clear()
//        gridViews.addAll(
//            listOf(
//                binding.vitalsOrGeneralInfoGridView,
//                binding.scheduleOrOrderGridView,
//                binding.careTeamGridView,
//                binding.staffHistoryGridView
//            )
//        )
//        for (i in (gridViews.size - 1) downTo 0) {
//            val gridView = gridViews[i]
//            if (gridView.visibility != View.VISIBLE) {
//                gridViews.remove(gridView)
//            }
//            if (gridView == binding.scheduleOrOrderGridView &&
//                binding.mhoBannerImageView.visibility == View.VISIBLE
//            ) {
//                myHealthOneRow = i
//                if (gridView.visibility == View.VISIBLE) {
//                    myHealthOneRow++
//                }
//            }
//        }
//        while (selectedGridViewIndex > gridViews.size - 1) {
//            selectedGridViewIndex--
//        }
//
//        postDelayed(100L) {
//            focusCell(selectedGridViewIndex, getSelectedCellIndex(selectedGridViewIndex), true)
//        }
//    }
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentHospitalBinding.inflate(inflater, container, false)
//        binding.model = mainViewModel
//        binding.lifecycleOwner = this
//        return binding.root
//    }
//
//
//    override fun rightPressed() {
//        if (isShareFeedbackDialogShown) {
//            return
//        }
//        if (myHealthOneRowIsSelected) {
//            return
//        }
//        super.rightPressed()
//    }
//
//
//    override fun leftPressed() {
//        if (isShareFeedbackDialogShown) {
//            return
//        }
//        if (myHealthOneRowIsSelected) {
//            return
//        }
//        super.leftPressed()
//    }
//
//    override fun upPressed() {
//        if (isShareFeedbackDialogShown) {
//            shareFeedbackDialogUpPressedState = true
//            return
//        }
//        super.upPressed()
//    }
//
//    override fun downPressed() {
//        if (isShareFeedbackDialogShown) {
//            shareFeedbackDialogDownPressedState = true
//            return
//        }
//        super.downPressed()
//    }
//
//    override fun selectPressed() {
//        if (isShareFeedbackDialogShown) {
//            shareFeedbackDialogCenterPressedState = true
//            return
//        }
//        if (selectedGridViewIndex < 0 || gridViews.size < 1 || myHealthOneRowIsSelected) {
//            return
//        }
//        val selectedGridView = gridViews[selectedGridViewIndex]
//        val selectedAdapter = selectedGridView.adapter as? HospitalAdapter ?: return
//        val items = selectedAdapter.items ?: return
//        val bundle = Bundle()
//        when (selectedAdapter.currentHealthType) {
//            HospitalType.VITALS -> {
//                val selectedVitalSign = items[selectedAdapter.selectedIndex] as Vital
//                bundle.putString("vitalSignName", selectedVitalSign.displayName)
//                navigate(
//                    navController, R.id.action_myHealthFragment_to_vitalSignFragment,
//                    "My_Health_Vitals_Opened",
//                    mapOf("Type" to selectedVitalSign.displayName), bundle
//                )
//            }
//
//            HospitalType.SCHEDULE -> {
//                val selectedScheduledActivity =
//                    items[selectedAdapter.selectedIndex] as ScheduledActivity
//                bundle.putString("scheduleId", selectedScheduledActivity.id)
//                navigate(
//                    navController, R.id.action_myHealthFragment_to_scheduleDetailFragment,
//                    null, null, bundle
//                )
//            }
//
//            HospitalType.GENERALINFO -> {
//                val selectedGeneralInfo = items[selectedAdapter.selectedIndex] as GeneralInfo
//                if (selectedGeneralInfo.card != null) {
//                    bundle.putString("painLevelDetail", selectedGeneralInfo.title)
//                    navigate(
//                        navController, R.id.action_myHealthFragment_to_painLevelDetailFragment,
//                        null,
//                        null, bundle
//                    )
//                }
//            }
//
//            else -> {}
//        }
//    }
//
//
//    override fun scrollToShowGridView(down: Boolean): Boolean {
//        val previousGridIndex = selectedGridViewIndex
//        if ((!myHealthOneRowIsSelected && myHealthOneRow > -1) &&
//            ((down && previousGridIndex == myHealthOneRow - 1) ||
//                    (!down) && previousGridIndex == myHealthOneRow)
//        ) {
//            scrollToShowHospitalOneBanner()
//            return false
//        } else if (myHealthOneRowIsSelected && !down && myHealthOneRow == selectedGridViewIndex + 1) {
//            selectedGridViewIndex++
//        }
//
//        myHealthOneRowIsSelected = false
//
//        val returnValue = super.scrollToShowGridView(down)
//
//        if (selectedGridViewIndex > -1 && gridViews.size > 0) {
//            val selectedGridView = gridViews[selectedGridViewIndex]
//            val selectedAdapter = selectedGridView.adapter as? HospitalAdapter ?: return returnValue
//            Util.analyticsEvent(
//                "My_Health_Row_Focused",
//                mapOf("Type" to selectedAdapter.myHealthType.typeName)
//            )
//        }
//
//        return returnValue
//    }
//
//    private fun scrollToShowHospitalOneBanner() {
//        if (selectedGridViewIndex < gridViews.size) {
//            val gridView = gridViews[selectedGridViewIndex]
//            val cellIndex = selectedIndicesByGridNumber[selectedGridViewIndex] ?: 0
//            val adapter = gridView.adapter as STBGridAdapter
//            val holder = gridView.findViewHolderForLayoutPosition(cellIndex)
//            if (holder != null) {
//                adapter.handleFocusChange(false, cellIndex, holder)
//            }
//        }
//
//        myHealthOneRowIsSelected = true
//        val screenHeight = getDeviceHeight(activity as FragmentActivity)
//        val midPoint = binding.mhoBannerImageView.y + (binding.mhoBannerImageView.height * .5)
//        val yPosition = midPoint - screenHeight / 2
//        scrollView.smoothScrollTo(0, yPosition.toInt())
//    }
//
//    private fun showShareFeedbackDialog(isFeedbackComplete: Boolean) {
//        val currentPopUpCount =
//            AppPreferences.instance.readIntegerPreference(requireContext(), KEY_POPUP_COUNT, 0)
//        AppPreferences.instance.writeIntegerPreference(KEY_POPUP_COUNT, currentPopUpCount + 1)
//        if (!isFeedbackComplete && (currentPopUpCount == 0 || currentPopUpCount == 2)) {
//            isShareFeedbackDialogShown = true
//            binding.screenOverLay.visibility = View.VISIBLE
//            binding.feedbackCardView.visibility = View.VISIBLE
//            binding.feedbackComposePopUp.visibility = View.VISIBLE
//            binding.feedbackComposePopUp.setContent {
//                val focusRequester = remember { FocusRequester() }
//
//                ShareFeedbackDialog(
//                    modifier = Modifier.focusRequester(focusRequester),
//                    shareFeedbackOnClick = {
//                        dismissShareFeedbackPopup()
//                        navController?.navigate(R.id.action_myHealthFragment_to_shareFeedbackFragment)
//                        mainViewModel.logHospitalEvent(SHARE_FEEDBACK_ON_MY_HEALTH_SELECT)
//                    },
//                    notNowOnClick = {
//                        dismissShareFeedbackPopup()
//                        mainViewModel.logHospitalEvent(NOT_NOW_SELECT)
//                    },
//                    upPressedState = shareFeedbackDialogUpPressedState,
//                    downPressedState = shareFeedbackDialogDownPressedState,
//                    centerPressedState = shareFeedbackDialogCenterPressedState
//                )
//                LaunchedEffect(shareFeedbackDialogUpPressedState) {
//                    if (shareFeedbackDialogUpPressedState)
//                        shareFeedbackDialogUpPressedState = false
//                }
//                LaunchedEffect(shareFeedbackDialogDownPressedState) {
//                    if (shareFeedbackDialogDownPressedState)
//                        shareFeedbackDialogDownPressedState = false
//                }
//                LaunchedEffect(shareFeedbackDialogCenterPressedState) {
//                    if (shareFeedbackDialogCenterPressedState) shareFeedbackDialogCenterPressedState =
//                        false
//                }
//            }
//        }
//    }
//
//    private fun dismissShareFeedbackPopup() {
//        isShareFeedbackDialogShown = false
//        binding.screenOverLay.visibility = View.GONE
//        binding.feedbackCardView.visibility = View.GONE
//        binding.feedbackComposePopUp.visibility = View.GONE
//    }
//
//    companion object { // static so we can use it from VitalSignFragment also
//        const val KEY_POPUP_COUNT = "pop up count"
//
//        @ExperimentalSerializationApi
//        fun supplyPlaceholderChartInfoIfNecessary(patientHospital: MutableLiveData<HospitalDataModel>) {
//            val patientData = patientHospital.value ?: return
//            if (patientData.vitalSigns?.size == 3) {
//                return
//            }
//            val vitalSigns = ArrayList<Vital>()
//            patientData.vitalSigns?.forEach { vitalSigns.add(it) }
//            vitalTypes@ for ((vitalType, placeholder) in TestData().vitalsTypesAndPlaceholders) {
//                for (vitalSign in vitalSigns) {
//                    if (vitalSign.displayName == vitalType) {
//                        continue@vitalTypes
//                    }
//                }
//                vitalSigns.add(Json.decodeFromString(placeholder))
//            }
//            patientData.vitalSigns = vitalSigns
//        }
//    }
//}