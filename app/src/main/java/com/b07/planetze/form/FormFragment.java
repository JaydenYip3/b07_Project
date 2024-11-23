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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.b07.planetze.R;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.form.field.ChoiceFragment;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;

public final class FormFragment extends Fragment {

    private FormViewModel model;

    public FormFragment() {}

    public static FormFragment newInstance() {
        return new FormFragment();
    }

    private static String fragmentTag(int counter) {
        return "formFrag" + counter;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        model = new ViewModelProvider(requireActivity())
                .get(FormViewModel.class);

        model.getForm().observe(getViewLifecycleOwner(), maybeForm -> {
            if (!(maybeForm instanceof Some<Form> some)) {
                return;
            }
            Form form = some.get();
            FormDefinition def = form.definition();

            FragmentManager mgr = requireActivity().getSupportFragmentManager();
            FragmentTransaction ft = mgr.beginTransaction();

            for (int i = model.getPreviousTagCounter();
                 i < model.getTagCounter(); i++) {
                Option.mapNull(mgr.findFragmentByTag(fragmentTag(i)))
                        .apply(ft::remove);
            }

            int count = model.getTagCounter();

            Util.enumerate(def.fields()).forEach((i, fieldDef) -> {
                FieldId<?> id = new FieldId<>(form.definition().id(), i);
                Fragment frag = fieldDef.field().createFragment(id);
                String tag = fragmentTag(count + i);

                ft.add(R.id.formFragmentContainer, frag, tag);
            });

            model.setTagCounter(count + def.fields().size());

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
