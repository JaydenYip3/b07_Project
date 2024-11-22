package com.b07.planetze.form.field;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.b07.planetze.R;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.exception.FieldFormException;
import com.b07.planetze.util.option.None;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;

public class ChoiceFragment extends Fragment {
    @NonNull private static final String FIELD_ID_KEY = "field";

    @NonNull private Option<FieldId<Integer>> field;

    /**
     * Use {@link ChoiceFragment#create} instead of calling this manually.
     */
    public ChoiceFragment() {
        field = new None<>();
    }

    public static ChoiceFragment create(@NonNull FieldId<?> field) {
        ChoiceFragment fragment = new ChoiceFragment();
        Bundle args = new Bundle();
        args.putParcelable(FIELD_ID_KEY, field);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = Option.mapNull(getArguments())
                .getOrThrow(new FieldFormException("No arguments provided"));

        @SuppressWarnings("unchecked")
        FieldId<Integer> f = Option
                .mapNull(args.getParcelable(FIELD_ID_KEY, FieldId.class))
                .getOrThrow(new FieldFormException("Invalid arguments"));

        field = new Some<>(f);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choice, container, false);
    }
}
