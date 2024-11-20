package com.example.planetze.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.b07.planetze.form.FieldId;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.FormBuilder;
import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.field.Choice;
import com.b07.planetze.form.field.ChoiceDefinition;
import com.b07.planetze.form.field.Int;
import com.b07.planetze.form.field.PositiveInt;
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

        FieldId<Choice> f1 = fb.addField(new ChoiceDefinition(choices, new None<>()));

        PositiveInt d = new PositiveInt(new None<>());

        FieldId<Int> f2 = fb.addField(d);
        FieldId<Int> f3 = fb.addField(d);

        Form form = fb.build();

        form.set(f2, new Int(2));
        form.set(f1, new Choice(1));

        assertTrue(form.submit().isErr());

        form.set(f3, new Int(5));

        Result<FormSubmission, List<Integer>> r = form.submit();

        r.match(sub -> {
            assertEquals(sub.get(f1).index(), 1);
            assertEquals(sub.get(f2).value(), 2);
            assertEquals(sub.get(f3).value(), 5);
        }, missing -> {
            fail();
        });
    }
}
