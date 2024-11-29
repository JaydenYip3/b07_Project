package com.b07.planetze.form;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.b07.planetze.R;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.form.field.ChoiceFragment;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;

import java.util.ArrayList;
import java.util.List;

public final class FormFragment extends Fragment {
    @NonNull private static final String TAG = "FormFragment";
    @NonNull private final List<Fragment> childFragments;
    private FormFragment() {
        childFragments = new ArrayList<>();
    }

    public static FormFragment newInstance() {
        return new FormFragment();
    }

    private static String fragmentTag(int counter) {
        return "formFrag" + counter;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        FormViewModel model = new ViewModelProvider(requireActivity())
                .get(FormViewModel.class);

        model.getForm().observe(getViewLifecycleOwner(), maybeForm -> {
            if (!(maybeForm instanceof Some<Form> some)) {
                return;
            }
            Form form = some.get();
            FormDefinition def = form.definition();

            Button submit = view.findViewById(R.id.form_submit);
            submit.setOnClickListener(btn -> {
                form.submit()
                        .map(Option::some)
                        .match(model::setSubmission, model::setMissingFields);
            });

            FragmentManager mgr = requireActivity().getSupportFragmentManager();
            FragmentTransaction ft = mgr.beginTransaction();

            childFragments.forEach(ft::remove);

            Util.enumerate(def.fields()).forEach((i, fieldDef) -> {
                FieldId<?> id = new FieldId<>(form.definition().id(), i);
                Fragment frag = fieldDef.field().createFragment(id);

                ft.add(R.id.form_fragment_container, frag);
                childFragments.add(frag);
            });

            ft.commit();
        });
    }

    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_form, container, false);
    }
}
