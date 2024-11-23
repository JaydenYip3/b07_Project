package com.example.planetze.form;

import static com.b07.planetze.util.option.Option.some;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.form.definition.FormBuilder;
import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.field.ChoiceField;
import com.b07.planetze.form.field.IntField;
import com.b07.planetze.util.immutability.ImmutableList;
import com.b07.planetze.util.option.None;
import com.b07.planetze.util.option.Some;
import com.b07.planetze.util.result.Result;

import org.junit.Test;

import java.util.List;

public class FormTest {
    @Test
    public void test() {
        FormBuilder fb = new FormBuilder();

        FieldId<Integer> f1 = fb.add("f1", ChoiceField
                .withChoices("c1", "c2", "c3")
                .initially(some(1)));

        FieldId<Integer> f2 = fb.add("f2", IntField
                .POSITIVE
                .initially(some(3)));
        FieldId<Integer> f3 = fb.add("f3", IntField.POSITIVE);

        FormDefinition def = fb.build();
        Form form = def.createForm();

        form.set(f2, 2);
        form.set(f1, 1);

        assertTrue(form.submit().isError());

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
