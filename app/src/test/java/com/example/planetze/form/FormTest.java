package com.example.planetze.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.b07.planetze.form.FieldId;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.FormBuilder;
import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.field.ChoiceField;
import com.b07.planetze.form.field.PositiveIntField;
import com.b07.planetze.util.ImmutableList;
import com.b07.planetze.util.option.None;
import com.b07.planetze.util.result.Result;

import org.junit.Test;

import java.util.List;

public class FormTest {
    @Test
    public void test() {
        FormBuilder fb = new FormBuilder();

        ImmutableList<String> choices = new ImmutableList<>(new String[] {
                "c1", "c2", "c3"
        });

        FieldId<Integer> f1 = fb.add(new ChoiceField(choices, new None<>()));

        PositiveIntField d = new PositiveIntField(new None<>());

        FieldId<Integer> f2 = fb.add(d);
        FieldId<Integer> f3 = fb.add(d);

        Form form = fb.build();

        form.set(f2, 2);
        form.set(f1, 1);

        assertTrue(form.submit().isErr());

        form.set(f3, 5);

        Result<FormSubmission, List<Integer>> r = form.submit();

        r.match(sub -> {
            assertEquals(sub.get(f1), Integer.valueOf(1));
            assertEquals(sub.get(f2), Integer.valueOf(2));
            assertEquals(sub.get(f3), Integer.valueOf(5));
        }, missing -> {
            fail();
        });
    }
}
