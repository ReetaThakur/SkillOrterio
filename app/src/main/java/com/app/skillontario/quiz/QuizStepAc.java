package com.app.skillontario.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.skillontario.adapter.QuizStepAdapter;
import com.app.skillontario.adapter.TabAdapter;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.interfaceClass.Controller;
import com.app.skillontario.models.quizModel.AnswerModel;
import com.app.skillontario.models.quizModel.QuizResultModel;
import com.app.skillontario.models.quizModel.ResultModel;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.QuizAcBinding;
import com.app.skillorterio.databinding.QuizStepAcBinding;
import com.app.skillorterio.databinding.TakeQuizAcBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;

public class QuizStepAc extends BaseActivity implements ApiResponseErrorCallback, Controller {
    private QuizStepAcBinding binding;
    int count = 1;
    int countNextQuestion;
    int countNextQuestionPlus;
    QuizStepAdapter quizStepAdapter1;
    ArrayList<String> questionList = new ArrayList<>();
    ArrayList<String> optList = new ArrayList<>();
    ArrayList<ResultModel> responseModelList = new ArrayList<>();
    ArrayList<ResultModel> responseModelListAfterFive = new ArrayList<>();
    HashMap<Integer, ArrayList<String>> ansHashMap = new HashMap<>();
    HashMap<Integer, ArrayList<String>> finalAnsHashMap = new HashMap<>();
    ArrayList<AnswerModel> answerModels = new ArrayList<>();
    boolean callFinal = false;
    public static ArrayList<QuizResultModel> quizFinalResultModel = new ArrayList<>();
    boolean checkCLick = false;

    @Override

    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (QuizStepAcBinding) viewBaseBinding;

        answerModels.clear();
        questionList.clear();
        optList.clear();
        validate();
        callQuizApi();
        next();

        //binding.progressQues
    }

    private void next() {
        binding.done.setOnClickListener(v -> {

            if (checkCLick) {
                checkCLick = false;
                if (callFinal) {
                    if (count == binding.myProgress.getMax()) {
                        // startActivity(new Intent(this, QuizAc.class));
                        callFinalApi(finalAnsHashMap);
                        return;
                    }
                }
                count++;
                if (count <= countNextQuestion) {
                    countNotify();
                }

                if (count == countNextQuestionPlus) {
                    countNotify();
                    callQuizApiAfterFive(ansHashMap);
                } else if (count > countNextQuestionPlus) {
                    countNotify();
                }
            } else {
                showToast("Select any one answer");
            }
        });
        binding.close.setOnClickListener(v -> {
            onBackPressed();
           /* if (count == 1) {
                onBackPressed();
            }
            count--;
            countNotify();*/

        });
    }

    void callQuizApi() {
        HashMap<String, Object> object = new HashMap<>();

        object.put("quesType", "1");   /// 1 = sector, 2 = trade
        object.put("catId", "");
        object.put("sectorArr", "");

        API_INTERFACE.getQuizListQuestion(object).enqueue(
                new ApiCallBack<>(QuizStepAc.this, this, 106, true));

    }

    void callQuizApiAfterFive(HashMap<Integer, ArrayList<String>> ansHashMap) {

        ArrayList<String> value = new ArrayList<>();

        HashMap<String, Object> object = new HashMap<>();

        object.put("quesType", "1");
        object.put("catId", "");

        for (Map.Entry<Integer, ArrayList<String>> entry : ansHashMap.entrySet()) {
            //int key = entry.getKey();
            ArrayList<String> value1 = entry.getValue();

            for (int i = 0; i < value1.size(); i++) {
                value.add(value1.get(i));
            }
        }

        object.put("sectorArr", value);


        API_INTERFACE.getQuizListQuestion(object).enqueue(
                new ApiCallBack<>(QuizStepAc.this, this, 107, true));

    }


    void callFinalApi(HashMap<Integer, ArrayList<String>> ansHashMap) {

        ArrayList<String> value = new ArrayList<>();

        HashMap<String, Object> object = new HashMap<>();


        object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));

        for (Map.Entry<Integer, ArrayList<String>> entry : ansHashMap.entrySet()) {
            // int key = entry.getKey();
            ArrayList<String> value1 = entry.getValue();
            for (int i = 0; i < value1.size(); i++) {
                value.add(value1.get(i));
            }
        }

        object.put("profileIds", value);


        API_INTERFACE.getQuizResult(object).enqueue(
                new ApiCallBack<>(QuizStepAc.this, this, 108, true));

    }

    private void countNotify() {
        binding.myProgress.setProgress(count);
        binding.tvCount.setText(count + "/" + binding.myProgress.getMax());

        //validate();
        if (callFinal) {
            nextQuestionAfterFive(count);
        } else {
            if (count <= countNextQuestion)
                nextQuestion(count);
            else {
                if (count != countNextQuestionPlus)
                    nextQuestionAfterFive(count);
            }
        }
    }

    private void validate() {  ///  count 2  for image question
        checkCLick = false;
        QuizStepAdapter quizStepAdapter = new QuizStepAdapter(getApplicationContext(), count, questionList, this, optList);
        binding.rvItems.setVisibility(View.VISIBLE);
        binding.progressQues.setVisibility(View.GONE);
        if (count == 2) {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            binding.rvItems.setLayoutManager(mLayoutManager);
            binding.rvItems.setAdapter(quizStepAdapter);
        } else if (count == 4) {
            binding.rvItems.setVisibility(View.GONE);
            binding.progressQues.setVisibility(View.VISIBLE);
        } else {  // work on else part current

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            binding.rvItems.setLayoutManager(mLayoutManager);
            binding.rvItems.setAdapter(quizStepAdapter);
        }

    }

    private void nextQuestion(int count) {
        questionList.clear();
        optList.clear();
        answerModels.clear();

        try {
            if (count > 0 && count <= responseModelList.size()) {

                binding.tvQuestion.setText(responseModelList.get(count - 1).getQuestion());

                for (int i = 0; i < responseModelList.get(count - 1).getAnswer().size(); i++) {
                    answerModels.add(responseModelList.get(count - 1).getAnswer().get(i));
                    questionList.add(responseModelList.get(count - 1).getAnswer().get(i).getText());
                    optList.add(responseModelList.get(count - 1).getAnswer().get(i).getOpt());

                    if (responseModelList.get(count - 1).getQuesType() == 1) {
                        binding.textCons.setText("Sector");
                    } else if (responseModelList.get(count - 1).getQuesType() == 2) {
                        binding.textCons.setText("Trade");
                    }

                }
            }
        } catch (Exception e) {
        }


        quizStepAdapter1 = new QuizStepAdapter(getApplicationContext(), 1, questionList, this::callback, optList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvItems.setLayoutManager(mLayoutManager);
        binding.rvItems.setAdapter(quizStepAdapter1);
    }

    private void nextQuestionAfterFive(int count) {
        questionList.clear();
        optList.clear();
        answerModels.clear();
        try {
            if (count > 0 && count <= responseModelListAfterFive.size()) {

                binding.tvQuestion.setText(responseModelListAfterFive.get(count - 1).getQuestion());

                for (int i = 0; i < responseModelListAfterFive.get(count - 1).getAnswer().size(); i++) {
                    answerModels.add(responseModelListAfterFive.get(count - 1).getAnswer().get(i));
                    questionList.add(responseModelListAfterFive.get(count - 1).getAnswer().get(i).getText());
                    optList.add(responseModelListAfterFive.get(count - 1).getAnswer().get(i).getOpt());

                    if (responseModelListAfterFive.get(count - 1).getQuesType() == 1) {
                        binding.textCons.setText("Sector");
                    } else if (responseModelListAfterFive.get(count - 1).getQuesType() == 2) {
                        binding.textCons.setText("Trade");
                    }
                }
            }
        } catch (Exception e) {
        }


        quizStepAdapter1 = new QuizStepAdapter(getApplicationContext(), 1, questionList, this::callback, optList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvItems.setLayoutManager(mLayoutManager);
        binding.rvItems.setAdapter(quizStepAdapter1);
    }


    @Override
    protected int getLayoutById() {
        return R.layout.quiz_step_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        callFinal = false;
        if (flag == 106) {

            ansHashMap.clear();
            BaseResponseModel<ArrayList<ResultModel>> responseModel = (BaseResponseModel<ArrayList<ResultModel>>) responseObject;
            responseModelList = responseModel.output;
            countNextQuestion = responseModelList.size();
            countNextQuestionPlus = countNextQuestion + 1;
            binding.myProgress.setMax(responseModelList.size());
            binding.tvCount.setText(1 + "/" + responseModelList.size());
            nextQuestion(1);

            try {
                if (responseModelList.get(0).getQuesType() == 1) {
                    binding.textCons.setText("Sector");
                } else if (responseModelList.get(0).getQuesType() == 2) {
                    binding.textCons.setText("Trade");
                }
            } catch (Exception e) {
            }

        } else if (flag == 107) {
            finalAnsHashMap.clear();
            BaseResponseModel<ArrayList<ResultModel>> responseModel = (BaseResponseModel<ArrayList<ResultModel>>) responseObject;
            responseModelListAfterFive = responseModel.output;

            if (responseModelListAfterFive.size() > 0) {
                if (responseModelListAfterFive.get(0).getQuesType() == 1) {

                    for (int i = 0; i < responseModelListAfterFive.size(); i++) {
                        responseModelList.add(responseModelListAfterFive.get(i));
                    }

                    countNextQuestion = responseModelList.size();
                    countNextQuestionPlus = countNextQuestion + 1;
                    binding.myProgress.setMax(responseModelList.size());
                    binding.tvCount.setText(count + "/" + responseModelList.size());
                    nextQuestion(count);
                    binding.myProgress.setProgress(count);
                    binding.textCons.setText("Sector");


                } else {
                    callFinal = true;
                    count = 1;
                    binding.myProgress.setMax(responseModelListAfterFive.size());
                    binding.myProgress.setProgress(count);
                    binding.tvCount.setText(1 + "/" + responseModelListAfterFive.size());
                    nextQuestionAfterFive(count);

                }
            }

        } else if (flag == 108) {
            BaseResponseModel<ArrayList<QuizResultModel>> responseModel = (BaseResponseModel<ArrayList<QuizResultModel>>) responseObject;
            try {
                if (responseModel.getStatus()) {
                    if (quizFinalResultModel == null) {
                        quizFinalResultModel = new ArrayList<>();
                    }
                    quizFinalResultModel.clear();
                    quizFinalResultModel.addAll(responseModel.getOutput());
                    startActivity(new Intent(this, QuizAc.class));
                    finish();
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }


    @Override
    public void callback(int pos) {  /// get Ans result
        checkCLick = true;
        if (callFinal) {
            finalAnsHashMap.put(count, (ArrayList<String>) answerModels.get(pos).getCatIds());
        } else {
            if (count > countNextQuestion) {
                finalAnsHashMap.put(count, (ArrayList<String>) answerModels.get(pos).getCatIds());
            } else
                ansHashMap.put(count, (ArrayList<String>) answerModels.get(pos).getCatIds());
        }
    }
}