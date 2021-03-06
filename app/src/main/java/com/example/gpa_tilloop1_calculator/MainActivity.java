package com.example.gpa_tilloop1_calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    ArrayList<TextInputLayout> gradeLayouts;
    ArrayList<TextInputEditText> gradeTextBoxes;
    final float GPA_LOWER_LIMIT = 60;
    final float GPA_MID_LIMIT = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gradeLayouts = new ArrayList<TextInputLayout>();
        gradeLayouts.add((TextInputLayout)findViewById(R.id.layout1));
        gradeLayouts.add((TextInputLayout)findViewById(R.id.layout2));
        gradeLayouts.add((TextInputLayout)findViewById(R.id.layout3));
        gradeLayouts.add((TextInputLayout)findViewById(R.id.layout4));
        gradeLayouts.add((TextInputLayout)findViewById(R.id.layout5));

        Iterator<TextInputLayout> iter = gradeLayouts.iterator();
        while(iter.hasNext()){

            TextInputEditText tbCourse = (TextInputEditText)iter.next().getEditText();
            tbCourse.addTextChangedListener(new TextWatcher(){
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Button btnGPA = (Button)findViewById(R.id.gpa_button);
                    btnGPA.setText(R.string.lblButton);
                    ConstraintLayout bgScroll = (ConstraintLayout)findViewById(R.id.constraint_layout1);
                    bgScroll.setBackgroundResource(R.color.white);
                    TextInputLayout layout = (TextInputLayout)tbCourse.getParent().getParent();
                    layout.setErrorEnabled(false);
                }
                public void afterTextChanged(Editable editable) {
                }
            });
        }
    }

    private boolean IfEmptyOrInvalid(ArrayList<TextInputLayout> layouts){
        Iterator<TextInputLayout> iterator = layouts.iterator();
        String strText;
        boolean isInvalid = false;
        while(iterator.hasNext()){
            TextInputLayout layout = iterator.next();
            strText = layout.getEditText().getText().toString();

            //Check if the textbox is empty
            if(isEmpty(strText)){
                layout.setErrorEnabled(true);
                layout.setError("Input required!");
                isInvalid = true;
            }
            else{
                //If textbox is not empty, check if the value is valid
                float gradeVal = Float.parseFloat(strText);
                if(gradeVal < 0 || gradeVal > 100) {
                    layout.setErrorEnabled(true);
                    layout.setError("Value should be between 0-100!");
                    isInvalid = true;
                }
                else
                    //Value is valid
                    layout.setErrorEnabled(false);
            }
        }
        return isInvalid;
    }

    private void setColor(View view, float gpa) {
        if(gpa <= GPA_LOWER_LIMIT) view.setBackgroundResource(R.color.red);
        else if(gpa <= GPA_MID_LIMIT) view.setBackgroundResource(R.color.yellow);
        else view.setBackgroundResource(R.color.green);
    }

    public void calculateGPA(View view) {
        if(!IfEmptyOrInvalid(gradeLayouts))
        {
            int total = 0;
            Iterator<TextInputLayout> iterator = gradeLayouts.iterator();
            while(iterator.hasNext()){
                TextInputLayout layout = iterator.next();
                total += Integer.parseInt(layout.getEditText().getText().toString());
                layout.getEditText().setText("");       //Clear the textboxes
            }
            float gpa = total/5;
            Button btnGPA = (Button)findViewById(R.id.gpa_button);
            btnGPA.setText("Your gpa is "+gpa);

            //Set Background color as per GPA value
            ConstraintLayout conLayout = (ConstraintLayout)findViewById(R.id.constraint_layout1);
            setColor(conLayout, gpa);
        }
    }
}