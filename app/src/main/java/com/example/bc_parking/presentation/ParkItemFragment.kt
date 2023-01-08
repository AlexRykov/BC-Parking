package com.example.bc_parking.presentation

//import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bc_parking.R
import com.example.bc_parking.domain.ParkItem
import com.google.android.material.textfield.TextInputLayout

class ParkItemFragment : Fragment() {


    private lateinit var viewModel: ParkItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var tilName: TextInputLayout
    private lateinit var tilFirm: TextInputLayout
    private lateinit var tilDateFrom: TextInputLayout
    private lateinit var tilDateTo: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etFirm: EditText
    private lateinit var etDateFrom: EditText
    private lateinit var etDateTo: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonClear: Button

    private var screenMode = MODE_UNKNOWN
    private var parkItemId = ParkItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity does not implement OnEditingFinishedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_park_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ParkItemViewModel::class.java]
        initViews(view)
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        viewModel.errorInputFirm.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilFirm.error = message
        }
        viewModel.errorInputDateFrom.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilDateFrom.error = message
        }
        viewModel.errorInputDateTo.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilDateTo.error = message
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }


    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addTextChangeListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        etFirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputFirm()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        etDateFrom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputDateFrom()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        etDateTo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputDateTo()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun launchEditMode() {
        viewModel.getParkItem(parkItemId)
        viewModel.parkItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etFirm.setText(it.firm)
            etDateFrom.setText(it.dateFrom)
            etDateTo.setText(it.dateTo)
            etCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            viewModel.editParkItem(
                etName.text?.toString(),
                etFirm.text?.toString(),
                etDateFrom.text?.toString(),
                etDateTo.text?.toString(),
                etCount.text?.toString()
            )
        }
        buttonClear.setOnClickListener {
            viewModel.parkItem.observe(viewLifecycleOwner) {
                etName.setText(TEXT)
                etFirm.setText(TEXT)
                etDateFrom.setText(TEXT)
                etDateTo.setText(TEXT)
            }
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addParkItem(
                etName.text?.toString(),
                etFirm.text?.toString(),
                etDateFrom.text?.toString(),
                etDateTo.text?.toString(),
                etCount.text?.toString()
            )
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(PARK_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            parkItemId = args.getInt(PARK_ITEM_ID, ParkItem.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilFirm = view.findViewById(R.id.til_firm)
        tilCount = view.findViewById(R.id.til_count)
        tilDateFrom = view.findViewById(R.id.til_dateFrom)
        tilDateTo = view.findViewById(R.id.til_dateTo)
        etName = view.findViewById(R.id.et_name)
        etFirm = view.findViewById(R.id.et_firm)
        etDateFrom = view.findViewById(R.id.et_dateFrom)
        etDateTo = view.findViewById(R.id.et_dateTo)
        etCount = view.findViewById(R.id.et_count)
        buttonSave = view.findViewById(R.id.save_button)
        buttonClear = view.findViewById(R.id.clear_button)
    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }

    companion object {

        private const val SCREEN_MODE = "extra_mode"
        private const val TEXT = "clear"
        private const val PARK_ITEM_ID = "extra_park_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""


        fun newInstanceAddItem(): ParkItemFragment {
            return ParkItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(parkItemID: Int): ParkItemFragment {
            return ParkItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(PARK_ITEM_ID, parkItemID)
                }
            }
        }
    }
}
