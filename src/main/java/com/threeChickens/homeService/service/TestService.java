package com.threeChickens.homeService.service;

import com.threeChickens.homeService.dto.test.*;
import com.threeChickens.homeService.entity.*;
import com.threeChickens.homeService.enums.QuestionType;
import com.threeChickens.homeService.enums.UserRole;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.repository.QuestionRepository;
import com.threeChickens.homeService.repository.TestRepository;
import com.threeChickens.homeService.repository.TestResultRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;


    public Test getTestById(String id){
        return testRepository.findById(id).orElseThrow(
                () -> new AppException(StatusCode.TEST_NOT_FOUND)
        );
    }

    public GetTestResultDto doTest(String testId, CreateTestResultDto createTestResultDto){
        Test test = getTestById(testId);
        User freelancer = userService.getByIdAndRole(createTestResultDto.getFreelancerId(), UserRole.FREELANCER);
        TestResult testResult = modelMapper.map(createTestResultDto, TestResult.class);

        AtomicInteger point = new AtomicInteger();
        AtomicInteger numOfCorrect = new AtomicInteger();

        testResult.setTest(test);
        testResult.setFreelancer(freelancer);

        Set<AnswerForQuestion> answerForQuestions  = createTestResultDto.getAnswerForQuestions().stream().map(
                answerDto -> {
                    Question question = test.getQuestions().stream().filter(q-> Objects.equals(q.getId(), answerDto.getQuestionId())).findFirst().orElseThrow(
                            () -> new AppException(StatusCode.QUESTION_NOT_FOUND)
                    );

                    AnswerForQuestion answerForQuestion = AnswerForQuestion.builder().build();
                    answerForQuestion.setQuestion(question);
                    answerForQuestion.setTestResult(testResult);

                    if(question.getType()==QuestionType.MULTICHOICE){
                        Choice choice = question.getChoices().stream().filter(c-> Objects.equals(c.getId(), answerDto.getChoiceId())).findFirst().orElseThrow(
                                () -> new AppException(StatusCode.CHOICE_NOT_FOUND)
                        );
                        Choice rightChoice = question.getChoices().stream().filter(Choice::isAnswer).findFirst().orElseThrow(
                                () -> new AppException(StatusCode.CHOICE_NOT_FOUND)
                        );
                        boolean right = Objects.equals(choice.getId(), rightChoice.getId());
                        point.addAndGet(right ? 1 : 0);
                        numOfCorrect.addAndGet(right ? 1 : 0);
                        answerForQuestion.setChoice(choice);
                        answerForQuestion.setCorrect(right);
                    }else{
                        answerForQuestion.setContent(answerDto.getContent());
                    }

                    return answerForQuestion;
                }
        ).collect(Collectors.toSet());

        testResult.setAnswerForQuestions(answerForQuestions);
        testResult.setNumOfCorrectAnswers(numOfCorrect.get());
        testResult.setPoint(point.get());

        TestResult finalTestResult = testResultRepository.save(testResult);

        return modelMapper.map(finalTestResult, GetTestResultDto.class);
    }

    public List<GetQuestionDto> getQuestionsByTestId(String testId, Integer questionCount){
        Test test = getTestById(testId);
        List<Question> questions = test.getQuestions().stream().filter(question->!question.isDeleted()).toList();

        if (questionCount != null && questionCount > 0) {
            // Create a mutable list from the original list of questions
            List<Question> mutableQuestions = new ArrayList<>(questions);

            // Shuffle the mutable list of questions
            Collections.shuffle(mutableQuestions, new Random(3));

            // Limit the number of questions according to `questionCount`
            mutableQuestions = mutableQuestions.stream()
                    .limit(questionCount)
                    .collect(Collectors.toList()); // Use collect to get a mutable list

            // Now mutableQuestions contains the shuffled and limited questions
            questions = mutableQuestions; // If you need to assign it back to questions
        }

        return questions.stream().map(
                question -> {
                    GetQuestionDto questionDto = modelMapper.map(question, GetQuestionDto.class);
                    Set<ChoiceDto> choices=  questionDto.getChoices().stream().filter(choice->!choice.isDeleted()).collect(Collectors.toSet());
                    questionDto.setChoices(choices);
                    return questionDto;
                }
        ).toList();
    }


    public GetQuestionDto addQuestion(String testId, CreateQuestionDto createQuestionDto) {
        Test test = getTestById(testId);
        Question question;
        try {
            question = modelMapper.map(createQuestionDto, Question.class);
        } catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.QUESTION_TYPE_INVALID);
        }

        question.setTest(test);

        if(createQuestionDto.getChoices()!=null){
            Set<Choice> choices = createQuestionDto.getChoices().stream().map(
                    choiceDto -> {
                        Choice choice = modelMapper.map(choiceDto, Choice.class);
                        choice.setQuestion(question);
                        return choice;
                    }
            ).collect(Collectors.toSet());
            question.setChoices(choices);
        }

        Question resultQuestion = questionRepository.save(question);
        return modelMapper.map(resultQuestion, GetQuestionDto.class);
    }

    public Question getQuestionById(String id){
        return questionRepository.findByIdAndDeletedIsFalse(id).orElseThrow(
                () -> new AppException(StatusCode.QUESTION_NOT_FOUND)
        );
    }

//    public Choice getChoiceById(String id){
//        return choiceRepository.findByIdAndDeletedIsFalse(id).orElseThrow(
//                () -> new AppException(StatusCode.CHOICE_NOT_FOUND)
//        );
//    }

//    public GetChoiceDto addChoice(String questionId, CreateChoiceDto createChoiceDto) {
//        Question question = getQuestionById(questionId);
//
//        boolean hasRightChoice = question.getChoices().stream().anyMatch(choice -> !choice.isDeleted());
//
//        Choice choice = modelMapper.map(createChoiceDto, Choice.class);
//        choice.setAnswer(!hasRightChoice);
//
//        choice.setQuestion(question);
//        choice = choiceRepository.save(choice);
//        return modelMapper.map(choice, GetChoiceDto.class);
//    }

    public void deleteQuestion(String questionId){
        Question question = getQuestionById(questionId);
        question.setDeleted(true);
        questionRepository.save(question);
    }

//    public void deleteChoice(String id){
//        Choice choice = getChoiceById(id);
//        if(choice.isAnswer()){
//            throw new AppException(StatusCode.DELETE_RIGHT_CHOICE);
//        }
//        choice.setDeleted(true);
//        choiceRepository.save(choice);
//    }

    public GetQuestionDto updateQuestion(String questionId, UpdateQuestionDto updateQuestionDto) {
        Question question = getQuestionById(questionId);
        if(updateQuestionDto.getContent()!=null){
            question.setContent(updateQuestionDto.getContent());
        }
        if(updateQuestionDto.getType()!=null){
            question.setType(QuestionType.valueOf(updateQuestionDto.getType()));
        }

        if(updateQuestionDto.getChoices() !=null){
            updateQuestionDto.getChoices().forEach(
                    choiceDto -> {
                        Choice choice = choiceDto.getId()!=null ? question.getChoices().stream().filter(ch-> choiceDto.getId().equals(ch.getId())).findFirst().orElse(
                                null
                        ): null;
                        if(choice!=null){
                            modelMapper.map(choiceDto, choice);
                        }else{
                            choice = modelMapper.map(choiceDto, Choice.class);
                        }
                        choice.setQuestion(question);
                        question.getChoices().add(choice);
                    }
            );
        }

        Question resultQuestion = questionRepository.save(question);

        Set<Choice> resultChoices = resultQuestion.getChoices().stream().filter(choice->!choice.isDeleted()).collect(Collectors.toSet());

        resultQuestion.setChoices(resultChoices);

        return modelMapper.map(resultQuestion, GetQuestionDto.class);
    }

//    public GetChoiceDto updateChoice(String choiceId, CreateChoiceDto createChoiceDto){
//        Choice choice = getChoiceById(choiceId);
//
//        Question question = choice.getQuestion();
//
//        if(question==null){
//            throw new AppException(StatusCode.QUESTION_TYPE_INVALID);
//        }
//
//        boolean previousRightChoice = choice.isAnswer();
//        boolean currentRightChoice = createChoiceDto.isAnswer();
//
//        modelMapper.map(createChoiceDto, choice);
//
//        if(!previousRightChoice && currentRightChoice){
//            Choice rightChoice = question.getChoices().stream().filter(Choice::isAnswer).findFirst().orElse(null);
//
//            if(rightChoice != null){
//                rightChoice.setAnswer(false);
//                choiceRepository.save(rightChoice);
//            }
//            choice.setAnswer(true);
//        }else{
//            choice.setAnswer(previousRightChoice);
//        }
//
//        choice = choiceRepository.save(choice);
//        return modelMapper.map(choice, GetChoiceDto.class);
//    }

}
