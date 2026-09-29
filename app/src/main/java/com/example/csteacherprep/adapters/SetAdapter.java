package com.example.csteacherprep.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csteacherprep.R;
import com.example.csteacherprep.utils.AdsManager;
import com.example.csteacherprep.utils.SetAccessManager;

import java.util.List;

public class SetAdapter
        extends RecyclerView.Adapter<SetAdapter.SetViewHolder> {

    private final List<String> setNames;
    private final List<Integer> questionCounts;

    private final OnSetClickListener listener;
    private final OnDeleteClickListener deleteListener;
    private final OnManageQuestionsClickListener manageQuestionsListener;
    private final OnEditSetClickListener editSetListener;

    // Lock system
    private final boolean lockEnabled;
    private final String lockKeyPrefix;

    public interface OnSetClickListener {
        void onSetClick(String setName);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(String setName);
    }

    public interface OnManageQuestionsClickListener {
        void onManageQuestionsClick(String setName);
    }

    public interface OnEditSetClickListener {
        void onEditSetClick(String setName);
    }

    // ---------------------------------------------------------
    // PYQ / Practice / Topic Wise - Normal constructor
    // ---------------------------------------------------------
    public SetAdapter(
            List<String> setNames,
            List<Integer> questionCounts,
            OnSetClickListener listener) {

        this(
                setNames,
                questionCounts,
                false,
                "",
                listener,
                null,
                null,
                null
        );
    }

    // ---------------------------------------------------------
    // PYQ / Practice / Topic Wise - Locked constructor
    // ---------------------------------------------------------
    public SetAdapter(
            List<String> setNames,
            List<Integer> questionCounts,
            boolean lockEnabled,
            String lockKeyPrefix,
            OnSetClickListener listener) {

        this(
                setNames,
                questionCounts,
                lockEnabled,
                lockKeyPrefix,
                listener,
                null,
                null,
                null
        );
    }

    // ---------------------------------------------------------
    // Self Designed - Delete
    // ---------------------------------------------------------
    public SetAdapter(
            List<String> setNames,
            List<Integer> questionCounts,
            OnSetClickListener listener,
            OnDeleteClickListener deleteListener) {

        this(
                setNames,
                questionCounts,
                false,
                "",
                listener,
                deleteListener,
                null,
                null
        );
    }

    // ---------------------------------------------------------
    // Self Designed - Delete + Manage Questions
    // ---------------------------------------------------------
    public SetAdapter(
            List<String> setNames,
            List<Integer> questionCounts,
            OnSetClickListener listener,
            OnDeleteClickListener deleteListener,
            OnManageQuestionsClickListener manageQuestionsListener) {

        this(
                setNames,
                questionCounts,
                false,
                "",
                listener,
                deleteListener,
                manageQuestionsListener,
                null
        );
    }

    // ---------------------------------------------------------
    // Self Designed - Full constructor
    // ---------------------------------------------------------
    public SetAdapter(
            List<String> setNames,
            List<Integer> questionCounts,
            OnSetClickListener listener,
            OnDeleteClickListener deleteListener,
            OnManageQuestionsClickListener manageQuestionsListener,
            OnEditSetClickListener editSetListener) {

        this(
                setNames,
                questionCounts,
                false,
                "",
                listener,
                deleteListener,
                manageQuestionsListener,
                editSetListener
        );
    }

    // ---------------------------------------------------------
    // Internal full constructor
    // ---------------------------------------------------------
    public SetAdapter(
            List<String> setNames,
            List<Integer> questionCounts,
            boolean lockEnabled,
            String lockKeyPrefix,
            OnSetClickListener listener,
            OnDeleteClickListener deleteListener,
            OnManageQuestionsClickListener manageQuestionsListener,
            OnEditSetClickListener editSetListener) {

        this.setNames = setNames;
        this.questionCounts = questionCounts;

        this.listener = listener;
        this.deleteListener = deleteListener;
        this.manageQuestionsListener = manageQuestionsListener;
        this.editSetListener = editSetListener;

        this.lockEnabled = lockEnabled;
        this.lockKeyPrefix =
                lockKeyPrefix == null ? "" : lockKeyPrefix;
    }

    @NonNull
    @Override
    public SetViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_set, parent, false);

        return new SetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull SetViewHolder holder,
            int position) {

        String setName = setNames.get(position);
        int questionCount = questionCounts.get(position);

        holder.tvSetName.setText(setName);

        holder.tvQuestionCount.setText(
                questionCount + " Questions"
        );

        // -----------------------------------------------------
        // LOCK STATUS
        // First set = always free
        // Other sets = locked until rewarded ad is watched
        // -----------------------------------------------------
        boolean locked = false;

        String accessKey =
                lockKeyPrefix
                        + position
                        + "_"
                        + setName;

        if (lockEnabled && position > 0) {

            locked = !SetAccessManager.isUnlocked(
                    holder.itemView.getContext(),
                    accessKey
            );
        }

        // Change arrow / lock icon
        if (locked) {

            holder.tvSetArrow.setText("🔒");

        } else {

            holder.tvSetArrow.setText("›");
        }

        final boolean isLocked = locked;

        // -----------------------------------------------------
        // OPEN QUIZ
        // -----------------------------------------------------
        holder.itemView.setOnClickListener(v -> {

            if (!isLocked) {

                // Free / already unlocked
                listener.onSetClick(setName);

                return;
            }

            // Locked set
            showUnlockDialog(
                    holder.itemView.getContext(),
                    accessKey,
                    setName
            );
        });

        // -----------------------------------------------------
        // EDIT SET
        // -----------------------------------------------------
        if (editSetListener != null) {

            holder.btnEditSet.setVisibility(View.VISIBLE);

            holder.btnEditSet.setOnClickListener(v ->
                    editSetListener.onEditSetClick(setName)
            );

        } else {

            holder.btnEditSet.setVisibility(View.GONE);
            holder.btnEditSet.setOnClickListener(null);
        }

        // -----------------------------------------------------
        // DELETE SET
        // -----------------------------------------------------
        if (deleteListener != null) {

            holder.btnDelete.setVisibility(View.VISIBLE);

            holder.btnDelete.setOnClickListener(v ->
                    deleteListener.onDeleteClick(setName)
            );

        } else {

            holder.btnDelete.setVisibility(View.GONE);
            holder.btnDelete.setOnClickListener(null);
        }

        // -----------------------------------------------------
        // MANAGE QUESTIONS
        // -----------------------------------------------------
        if (manageQuestionsListener != null) {

            holder.btnManageQuestions.setVisibility(
                    View.VISIBLE
            );

            holder.btnManageQuestions.setOnClickListener(v ->
                    manageQuestionsListener
                            .onManageQuestionsClick(setName)
            );

        } else {

            holder.btnManageQuestions.setVisibility(
                    View.GONE
            );

            holder.btnManageQuestions.setOnClickListener(null);
        }
    }

    // ---------------------------------------------------------
    // Unlock dialog
    // ---------------------------------------------------------
    private void showUnlockDialog(
            Context context,
            String accessKey,
            String setName) {

        new AlertDialog.Builder(context)

                .setTitle("Set Locked")

                .setMessage(
                        "Watch a rewarded ad to unlock this set "
                                + "for 24 hours."
                )

                .setNegativeButton(
                        "Cancel",
                        null
                )

                .setPositiveButton(
                        "Watch Ad",
                        (dialog, which) -> {

                            Activity activity =
                                    getActivity(context);

                            if (activity == null) {

                                Toast.makeText(
                                        context,
                                        "Unable to open ad right now.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            AdsManager.showRewardedAd(
                                    activity,
                                    () -> {

                                        // Unlock for 24 hours
                                        SetAccessManager.unlockFor24Hours(
                                                activity,
                                                accessKey
                                        );

                                        // Refresh this item
                                        int adapterPosition =
                                                findPosition(setName);

                                        if (adapterPosition != -1) {

                                            notifyItemChanged(
                                                    adapterPosition
                                            );
                                        }

                                        Toast.makeText(
                                                activity,
                                                "Set unlocked for 24 hours.",
                                                Toast.LENGTH_SHORT
                                        ).show();

                                        // Open the set
                                        if (listener != null) {

                                            listener.onSetClick(setName);
                                        }
                                    }
                            );
                        })

                .show();
    }

    // ---------------------------------------------------------
    // Find current position
    // ---------------------------------------------------------
    private int findPosition(String setName) {

        for (int i = 0; i < setNames.size(); i++) {

            if (setName.equals(setNames.get(i))) {
                return i;
            }
        }

        return -1;
    }

    // ---------------------------------------------------------
    // Get Activity from Context safely
    // ---------------------------------------------------------
    private Activity getActivity(Context context) {

        while (context instanceof ContextWrapper) {

            if (context instanceof Activity) {
                return (Activity) context;
            }

            context =
                    ((ContextWrapper) context).getBaseContext();
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return setNames.size();
    }

    // ---------------------------------------------------------
    // ViewHolder
    // ---------------------------------------------------------
    static class SetViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvSetArrow;
        TextView tvSetName;
        TextView tvQuestionCount;
        TextView btnEditSet;
        TextView btnDelete;
        TextView btnManageQuestions;

        public SetViewHolder(
                @NonNull View itemView) {

            super(itemView);

            tvSetArrow =
                    itemView.findViewById(
                            R.id.tvSetArrow
                    );

            tvSetName =
                    itemView.findViewById(
                            R.id.tvSetName
                    );

            tvQuestionCount =
                    itemView.findViewById(
                            R.id.tvQuestionCount
                    );

            btnEditSet =
                    itemView.findViewById(
                            R.id.btnEditSet
                    );

            btnDelete =
                    itemView.findViewById(
                            R.id.btnDelete
                    );

            btnManageQuestions =
                    itemView.findViewById(
                            R.id.btnManageQuestions
                    );
        }
    }
}