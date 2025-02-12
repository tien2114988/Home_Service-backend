package com.threeChickens.homeService.utils;

import com.threeChickens.homeService.entity.*;
import com.threeChickens.homeService.enums.*;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.repository.*;
import com.threeChickens.homeService.service.AdminService;
import com.threeChickens.homeService.service.BankService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class SeedDataUtil {
    @Autowired
    private BankService bankService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ChoiceRepository choiceRepository;
    @Autowired
    private TestResultRepository testResultRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private FreelancerWorkRepository freelancerWorkRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private WorkScheduleRepository workScheduleRepository;
    @Autowired
    private FreelancerTakePostRepository freelancerTakePostRepository;
    @Autowired
    private HouseCleaningRepository houseCleaningRepository;
    @Autowired
    private BabysittingRepository babysittingRepository;
    @Autowired
    private BabyRepository babyRepository;

    @Transactional
    public void seedData() throws IOException {
        bankService.initBanks();
        adminService.initAdminAccounts();
        initUsers();
    }

    @Transactional
    public void initTests(Work work1, Work work2, User freelancer1, User freelancer2, User freelancer3){
        Test test1 = Test.builder().testDuration(100).passedPoint(100).questionCount(10).work(work1).build();
        Test test2 = Test.builder().testDuration(90).passedPoint(100).questionCount(15).work(work2).build();
        testRepository.save(test1);
        testRepository.save(test2);

        Question question1 = Question.builder().test(test1).type(QuestionType.MULTICHOICE).content("1 + 1 = ?").build();
        Question question2 = Question.builder().test(test1).type(QuestionType.MULTICHOICE).content("7 + 2 = ?").build();
        Question question3 = Question.builder().test(test1).type(QuestionType.MULTICHOICE).content("8 + 9 = ?").build();
        Question question4 = Question.builder().test(test1).type(QuestionType.ESSAY).content("Tôi là ai ?").build();
        Question question5 = Question.builder().test(test1).type(QuestionType.ESSAY).content("Bạn học lớp mấy ?").build();
        Question question6 = Question.builder().test(test1).type(QuestionType.ESSAY).content("Bạn học ngành gì ?").build();
        Question question7 = Question.builder().test(test1).type(QuestionType.ESSAY).content("Bạn là ai ?").build();
        Question question8 = Question.builder().test(test1).type(QuestionType.ESSAY).content("Bạn tên gì vậy ?").build();
        Question question9 = Question.builder().test(test2).type(QuestionType.MULTICHOICE).content("120 - 1 = ?").build();
        Question question10 = Question.builder().test(test2).type(QuestionType.MULTICHOICE).content("178 + 8 = ?").build();
        Question question11 = Question.builder().test(test2).type(QuestionType.MULTICHOICE).content("Việt Nam có bao nhiêu tỉnh ?").build();
        Question question12 = Question.builder().test(test2).type(QuestionType.MULTICHOICE).content("1 năm có bao nhiêu tháng").build();
        Question question13 = Question.builder().test(test2).type(QuestionType.ESSAY).content("Ronaldo or Messi ?").build();
        Question question14 = Question.builder().test(test2).type(QuestionType.ESSAY).content("Đề tài đồ án này tên gì ?").build();
        Question question15 = Question.builder().test(test2).type(QuestionType.ESSAY).content("Sơn Tùng or Jack ?").build();
        Question question16 = Question.builder().test(test2).type(QuestionType.ESSAY).content("1 + 7 = ?").build();

        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);
        questionRepository.save(question4);
        questionRepository.save(question5);
        questionRepository.save(question6);
        questionRepository.save(question7);
        questionRepository.save(question8);
        questionRepository.save(question9);
        questionRepository.save(question10);
        questionRepository.save(question11);
        questionRepository.save(question12);
        questionRepository.save(question13);
        questionRepository.save(question14);
        questionRepository.save(question15);
        questionRepository.save(question16);

        // Question 1
        Choice choice1 = Choice.builder().question(question1).content("1").build();
        Choice choice2 = Choice.builder().question(question1).content("3").build();
        Choice choice3 = Choice.builder().question(question1).content("4").build();
        Choice choice4 = Choice.builder().question(question1).content("2").isAnswer(true).build();
        choiceRepository.save(choice1);
        choiceRepository.save(choice2);
        choiceRepository.save(choice3);
        choiceRepository.save(choice4);

        // Question 2
        Choice choice5 = Choice.builder().question(question2).content("6").build();
        Choice choice6 = Choice.builder().question(question2).content("10").build();
        Choice choice7 = Choice.builder().question(question2).content("8").isAnswer(true).build();
        Choice choice8 = Choice.builder().question(question2).content("9").build();
        choiceRepository.save(choice5);
        choiceRepository.save(choice6);
        choiceRepository.save(choice7);
        choiceRepository.save(choice8);

        // Question 3
        Choice choice9 = Choice.builder().question(question3).content("15").build();
        Choice choice10 = Choice.builder().question(question3).content("18").build();
        Choice choice11 = Choice.builder().question(question3).content("17").isAnswer(true).build();
        Choice choice12 = Choice.builder().question(question3).content("20").build();
        choiceRepository.save(choice9);
        choiceRepository.save(choice10);
        choiceRepository.save(choice11);
        choiceRepository.save(choice12);

        // Question 9
        Choice choice13 = Choice.builder().question(question9).content("121").build();
        Choice choice14 = Choice.builder().question(question9).content("118").build();
        Choice choice15 = Choice.builder().question(question9).content("119").isAnswer(true).build();
        Choice choice16 = Choice.builder().question(question9).content("120").build();
        choiceRepository.save(choice13);
        choiceRepository.save(choice14);
        choiceRepository.save(choice15);
        choiceRepository.save(choice16);

        // Question 10
        Choice choice17 = Choice.builder().question(question10).content("186").isAnswer(true).build();
        Choice choice18 = Choice.builder().question(question10).content("190").build();
        Choice choice19 = Choice.builder().question(question10).content("185").build();
        Choice choice20 = Choice.builder().question(question10).content("200").build();
        choiceRepository.save(choice17);
        choiceRepository.save(choice18);
        choiceRepository.save(choice19);
        choiceRepository.save(choice20);

        // Question 11
        Choice choice21 = Choice.builder().question(question11).content("60").build();
        Choice choice22 = Choice.builder().question(question11).content("63").isAnswer(true).build();
        Choice choice23 = Choice.builder().question(question11).content("64").build();
        Choice choice24 = Choice.builder().question(question11).content("62").build();
        choiceRepository.save(choice21);
        choiceRepository.save(choice22);
        choiceRepository.save(choice23);
        choiceRepository.save(choice24);

        // Question 12
        Choice choice25 = Choice.builder().question(question12).content("10").build();
        Choice choice26 = Choice.builder().question(question12).content("13").build();
        Choice choice27 = Choice.builder().question(question12).content("11").build();
        Choice choice28 = Choice.builder().question(question12).content("12").isAnswer(true).build();
        choiceRepository.save(choice25);
        choiceRepository.save(choice26);
        choiceRepository.save(choice27);
        choiceRepository.save(choice28);

        TestResult testResult1 = TestResult.builder()
                .startTime(LocalDateTime.of(2024,11,29, 2, 55))
                .endTime(LocalDateTime.of(2024,11,29, 3, 55))
                .numOfCorrectAnswers(10).point(10).test(test1).build();
        TestResult testResult2 = TestResult.builder()
                .startTime(LocalDateTime.of(2024,11,28, 2, 55))
                .endTime(LocalDateTime.of(2024,11,28, 3, 55))
                .numOfCorrectAnswers(100).point(100).test(test1).build();
        TestResult testResult3 = TestResult.builder()
                .startTime(LocalDateTime.of(2022,11,29, 2, 55))
                .endTime(LocalDateTime.of(2022,11,29, 3, 55))
                .numOfCorrectAnswers(10).point(10).test(test1).build();
        TestResult testResult4 = TestResult.builder()
                .startTime(LocalDateTime.of(2023,11,28, 2, 55))
                .endTime(LocalDateTime.of(2023,11,28, 3, 55))
                .numOfCorrectAnswers(100).point(100).test(test2).build();
        TestResult testResult5 = TestResult.builder()
                .startTime(LocalDateTime.of(2024,9,29, 2, 55))
                .endTime(LocalDateTime.of(2024,9,29, 3, 55))
                .numOfCorrectAnswers(10).point(10).test(test2).build();
        TestResult testResult6 = TestResult.builder()
                .startTime(LocalDateTime.of(2024,8,28, 2, 55))
                .endTime(LocalDateTime.of(2024,8,28, 3, 55))
                .numOfCorrectAnswers(100).point(100).test(test2).build();

        testResultRepository.save(testResult1);
        testResultRepository.save(testResult2);
        testResultRepository.save(testResult3);
        testResultRepository.save(testResult4);
        testResultRepository.save(testResult5);
        testResultRepository.save(testResult6);

        AnswerForQuestion answerForQuestion1 = AnswerForQuestion.builder()
                .question(question1)
                .choice(choice4)
                .testResult(testResult1)
                .correct(true)
                .build();
        AnswerForQuestion answerForQuestion2 = AnswerForQuestion.builder()
                .question(question2)
                .choice(choice5)
                .testResult(testResult1)
                .correct(true)
                .build();
        AnswerForQuestion answerForQuestion3 = AnswerForQuestion.builder()
                .question(question5)
                .testResult(testResult1)
                .content("12")
                .build();

        AnswerForQuestion answerForQuestion4 = AnswerForQuestion.builder()
                .question(question6)
                .content("hi")
                .testResult(testResult2)
                .build();
        AnswerForQuestion answerForQuestion5 = AnswerForQuestion.builder()
                .question(question2)
                .choice(choice5)
                .testResult(testResult2)
                .correct(true)
                .build();
        AnswerForQuestion answerForQuestion6 = AnswerForQuestion.builder()
                .question(question5)
                .testResult(testResult2)
                .content("12")
                .build();

        AnswerForQuestion answerForQuestion7 = AnswerForQuestion.builder()
                .question(question6)
                .content("hi")
                .testResult(testResult3)
                .build();
        AnswerForQuestion answerForQuestion8 = AnswerForQuestion.builder()
                .question(question7)
                .testResult(testResult3)
                .content("abcdefgh")
                .build();
        AnswerForQuestion answerForQuestion9 = AnswerForQuestion.builder()
                .question(question5)
                .testResult(testResult3)
                .content("12")
                .build();

        AnswerForQuestion answerForQuestion10 = AnswerForQuestion.builder()
                .question(question9)
                .choice(choice15)
                .testResult(testResult4)
                .correct(true)
                .build();
        AnswerForQuestion answerForQuestion11 = AnswerForQuestion.builder()
                .question(question10)
                .choice(choice16)
                .testResult(testResult4)
                .correct(false)
                .build();
        AnswerForQuestion answerForQuestion12 = AnswerForQuestion.builder()
                .question(question11)
                .testResult(testResult4)
                .choice(choice22)
                .correct(true)
                .build();

        AnswerForQuestion answerForQuestion13 = AnswerForQuestion.builder()
                .question(question13)
                .content("hi")
                .testResult(testResult5)
                .build();
        AnswerForQuestion answerForQuestion14 = AnswerForQuestion.builder()
                .question(question14)
                .content("hhabcudhfihfid")
                .testResult(testResult5)
                .build();
        AnswerForQuestion answerForQuestion15 = AnswerForQuestion.builder()
                .question(question15)
                .testResult(testResult5)
                .content("12")
                .build();

        AnswerForQuestion answerForQuestion16 = AnswerForQuestion.builder()
                .question(question9)
                .choice(choice15)
                .correct(true)
                .testResult(testResult6)
                .build();
        AnswerForQuestion answerForQuestion17 = AnswerForQuestion.builder()
                .question(question10)
                .testResult(testResult6)
                .choice(choice16)
                .correct(false)
                .build();
        AnswerForQuestion answerForQuestion18 = AnswerForQuestion.builder()
                .question(question15)
                .testResult(testResult6)
                .content("12")
                .build();

        answerRepository.save(answerForQuestion1);
        answerRepository.save(answerForQuestion2);
        answerRepository.save(answerForQuestion3);
        answerRepository.save(answerForQuestion4);
        answerRepository.save(answerForQuestion5);
        answerRepository.save(answerForQuestion6);
        answerRepository.save(answerForQuestion7);
        answerRepository.save(answerForQuestion8);
        answerRepository.save(answerForQuestion9);
        answerRepository.save(answerForQuestion10);
        answerRepository.save(answerForQuestion11);
        answerRepository.save(answerForQuestion12);
        answerRepository.save(answerForQuestion13);
        answerRepository.save(answerForQuestion14);
        answerRepository.save(answerForQuestion15);
        answerRepository.save(answerForQuestion16);
        answerRepository.save(answerForQuestion17);
        answerRepository.save(answerForQuestion18);

        FreelancerWorkService freelancerWorkService1 = FreelancerWorkService.builder()
                .status(FreelancerWorkStatus.INITIAL)
                .freelancer(freelancer1)
                .testResult(testResult1)
                .work(work1)
                .description("Làm dịch vụ có tâm lắm")
                .build();

        FreelancerWorkService freelancerWorkService2 = FreelancerWorkService.builder()
                .status(FreelancerWorkStatus.WORK)
                .freelancer(freelancer2)
                .testResult(testResult2)
                .work(work1)
                .description("Làm vì đam mê")
                .build();

        FreelancerWorkService freelancerWorkService3 = FreelancerWorkService.builder()
                .status(FreelancerWorkStatus.WORK)
                .freelancer(freelancer3)
                .testResult(testResult3)
                .work(work1)
                .description("Làm dịch vụ có tâm lắm")
                .build();

        FreelancerWorkService freelancerWorkService4 = FreelancerWorkService.builder()
                .status(FreelancerWorkStatus.WORK)
                .freelancer(freelancer1)
                .testResult(testResult4)
                .work(work2)
                .description("Làm dịch vụ có tâm lắm")
                .build();

        FreelancerWorkService freelancerWorkService5 = FreelancerWorkService.builder()
                .status(FreelancerWorkStatus.WORK)
                .freelancer(freelancer2)
                .testResult(testResult5)
                .work(work2)
                .description("Làm vì đam mê")
                .build();

        FreelancerWorkService freelancerWorkService6 = FreelancerWorkService.builder()
                .status(FreelancerWorkStatus.INITIAL)
                .freelancer(freelancer3)
                .testResult(testResult6)
                .work(work2)
                .description("Làm dịch vụ có tâm lắm")
                .build();

        freelancerWorkRepository.save(freelancerWorkService1);
        freelancerWorkRepository.save(freelancerWorkService2);
        freelancerWorkRepository.save(freelancerWorkService3);
        freelancerWorkRepository.save(freelancerWorkService4);
        freelancerWorkRepository.save(freelancerWorkService5);
        freelancerWorkRepository.save(freelancerWorkService6);
    }

    @Transactional
    public void initPosts(User customer1, User freelancer1, User freelancer2, User freelancer3, Work work1, Work work2, Address address){
        Post post1 = Post.builder()
                .customerNote("Nothing")
                .startTime(Time.valueOf("02:00:00"))
                .duration(10)
                .price(1000000)
                .status(PostStatus.INITIAL)
                .paymentType(PaymentType.QR)
                .isPayment(true)
                .totalFreelancer(2)
                .packageName(PackageName._1DAY)
                .totalWorkDay(1)
                .customer(customer1)
                .address(address)
                .work(work1)
                .build();

        Post post2 = Post.builder()
                .customerNote("Không có gì")
                .startTime(Time.valueOf("14:00:00"))
                .duration(210)
                .price(10000000)
                .status(PostStatus.SCHEDULED)
                .paymentType(PaymentType.CASH)
                .isPayment(false)
                .totalFreelancer(1)
                .packageName(PackageName._1MONTH)
                .totalWorkDay(2)
                .customer(customer1)
                .address(address)
                .work(work2)
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        WorkSchedule workSchedule1 = WorkSchedule.builder()
                .post(post1)
                .date(LocalDate.of(2024,12,1))
                .status(WorkScheduleStatus.INITIAL)
                .build();

        WorkSchedule workSchedule2 = WorkSchedule.builder()
                .post(post2)
                .date(LocalDate.of(2024,12,2))
                .status(WorkScheduleStatus.INITIAL)
                .build();

        WorkSchedule workSchedule3 = WorkSchedule.builder()
                .post(post2)
                .date(LocalDate.of(2024,12,5))
                .status(WorkScheduleStatus.INITIAL)
                .build();

        workScheduleRepository.save(workSchedule1);
        workScheduleRepository.save(workSchedule2);
        workScheduleRepository.save(workSchedule3);

        FreelancerTakePost freelancerTakePost1 = FreelancerTakePost.builder()
                .status(TakePostStatus.ACCEPTED)
                .post(post1)
                .freelancer(freelancer2)
                .build();

        FreelancerTakePost freelancerTakePost2 = FreelancerTakePost.builder()
                .status(TakePostStatus.ACCEPTED)
                .post(post1)
                .freelancer(freelancer3)
                .build();

        FreelancerTakePost freelancerTakePost3 = FreelancerTakePost.builder()
                .status(TakePostStatus.PENDING)
                .post(post2)
                .freelancer(freelancer1)
                .build();

        FreelancerTakePost freelancerTakePost4 = FreelancerTakePost.builder()
                .status(TakePostStatus.PENDING)
                .post(post2)
                .freelancer(freelancer2)
                .build();

        freelancerTakePostRepository.save(freelancerTakePost1);
        freelancerTakePostRepository.save(freelancerTakePost2);
        freelancerTakePostRepository.save(freelancerTakePost3);
        freelancerTakePostRepository.save(freelancerTakePost4);

        HouseCleaning houseCleaning = HouseCleaning.builder().area(100).post(post1).build();
        Babysitting babysitting = Babysitting.builder().numOfBaby(2).post(post2).build();

        houseCleaningRepository.save(houseCleaning);
        babysittingRepository.save(babysitting);

        Baby baby1 = Baby.builder().age(2).babysitting(babysitting).build();
        Baby baby2 = Baby.builder().age(3).babysitting(babysitting).build();

        babyRepository.save(baby1);
        babyRepository.save(baby2);
    }

    @Transactional
    public void initWorks(User customer1, User freelancer1, User freelancer2, User freelancer3, Address address){
        Work work1 = Work.builder().name("HOUSECLEANING")
                    .image("https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/169183/Originals/fba1a1bb-1-1.jpg")
                    .description("Công việc dọn dẹp nhà bao gồm các nhiệm vụ cơ bản để duy trì sự sạch sẽ và gọn gàng của không gian sống. Nhân viên sẽ chịu trách nhiệm lau chùi sàn nhà, vệ sinh nội thất, làm sạch cửa sổ, dọn dẹp phòng tắm và nhà bếp, cùng việc sắp xếp lại đồ đạc nếu cần. Dịch vụ này đặc biệt hữu ích cho các gia đình bận rộn hoặc những người không có thời gian để tự mình dọn dẹp. Sự chuyên nghiệp, chu đáo và tinh thần trách nhiệm là yếu tố quan trọng để đảm bảo khách hàng luôn hài lòng.").build();
        Work work2 = Work.builder().name("BABYSITTING")
                    .image("https://www.droppii.com/wp-content/uploads/2023/01/Phai-that-su-yeu-tre-con.jpg")
                    .description("Công việc trông trẻ tập trung vào việc chăm sóc và bảo vệ trẻ em trong khi cha mẹ vắng mặt. Nhân viên trông trẻ sẽ đảm bảo an toàn cho trẻ, hỗ trợ các hoạt động học tập và vui chơi, chuẩn bị bữa ăn nhẹ và giúp trẻ tuân thủ lịch sinh hoạt hàng ngày. Dịch vụ này yêu cầu sự tận tâm, kỹ năng giao tiếp tốt, và khả năng xử lý tình huống linh hoạt để mang lại sự yên tâm tuyệt đối cho phụ huynh.").build();
        workRepository.save(work1);
        workRepository.save(work2);

        initTests(work1, work2, freelancer1, freelancer2, freelancer3);
        initPosts(customer1, freelancer1, freelancer2, freelancer3, work1, work2, address);
    }



    @Transactional
    public void initBankAccounts(User customer1, User freelancer1, User freelancer2, User freelancer3){
        Bank bank1 = bankService.getByBin("970422");
        Bank bank2 = bankService.getByBin("970407");

        BankAccount bankAccount1 = BankAccount.builder()
                .accountName("NGUYEN DAI TIEN")
                .accountNumber("0346066323")
                .bank(bank1)
                .user(customer1)
                .build();

        BankAccount bankAccount2 = BankAccount.builder()
                .accountName("NGUYEN DAI TIEN")
                .accountNumber("0346066323")
                .bank(bank1)
                .user(freelancer1)
                .build();

        BankAccount bankAccount3 = BankAccount.builder()
                .accountName("NGUYEN TRUONG PHUOC THO")
                .accountNumber("0123456789")
                .bank(bank2)
                .user(freelancer2)
                .build();

        bankAccountRepository.save(bankAccount1);
        bankAccountRepository.save(bankAccount2);
        bankAccountRepository.save(bankAccount3);
    }

    @Transactional
    public void initUsers(){
        if(userRepository.count()==0){
            User customer1 = User.builder()
                    .avatar("https://w7.pngwing.com/pngs/340/946/png-transparent-avatar-user-computer-icons-software-developer-avatar-child-face-heroes.png")
                    .name("Nguyễn Đại Tiến")
                    .email("tien.nguyen2283@hcmut.edu.vn")
                    .role(UserRole.CUSTOMER)
                    .dob(new Date())
                    .balance(1000000)
                    .gender(Gender.MALE)
                    .phoneNumber("0346066323")
                    .reputationPoint(100)
                    .status(UserStatus.ACTIVE)
                    .build();
            User freelancer1 = User.builder()
                    .name("Tiến Nguyễn Đại")
                    .email("tiennguyen2283@gmail.com")
                    .role(UserRole.FREELANCER)
                    .dob(new Date())
                    .balance(1000000)
                    .gender(Gender.MALE)
                    .phoneNumber("0346066323")
                    .reputationPoint(100)
                    .status(UserStatus.ACTIVE)
                    .build();
            User freelancer2 = User.builder()
                    .avatar("https://icons.veryicon.com/png/o/miscellaneous/user-avatar/user-avatar-male-5.png")
                    .name("Trương Phước Thọ Nguyễn")
                    .email("phuoctho150420@gmail.com")
                    .role(UserRole.FREELANCER)
                    .dob(new Date())
                    .balance(6000000)
                    .gender(Gender.MALE)
                    .phoneNumber("0123456789")
                    .reputationPoint(100)
                    .status(UserStatus.ACTIVE)
                    .build();
            User freelancer3 = User.builder()
                    .avatar("https://e7.pngegg.com/pngimages/348/800/png-clipart-man-wearing-blue-shirt-illustration-computer-icons-avatar-user-login-avatar-blue-child.png")
                    .name("Tiến Dũng Bùi")
                    .email("dung.buitiendung03@hcmut.edu.vn")
                    .role(UserRole.FREELANCER)
                    .dob(new Date())
                    .balance(5000000)
                    .gender(Gender.MALE)
                    .phoneNumber("0123456789")
                    .reputationPoint(100)
                    .status(UserStatus.ACTIVE)
                    .build();

            userRepository.save(customer1);
            userRepository.save(freelancer1);
            userRepository.save(freelancer2);
            userRepository.save(freelancer3);

            Address address1 = Address.builder()
                    .customerName("Nguyễn Đại Tiến")
                    .phoneNumber("0346066323")
                    .detail("Đồng Nai, Việt Nam")
                    .placeId("ChIJJepKuFLZdDERVRZuS_HmSuA")
                    .isDefault(true)
                    .user(customer1)
                    .build();

            Address address2 = Address.builder()
                    .customerName("Nguyễn Tiến")
                    .phoneNumber("0346066323")
                    .detail("Quận 10, Hồ Chí Minh, Đường Tô Hiến Thành, Cư xá Bắc Hải, Phường 15, Quận 10, Hồ Chí Minh, Việt Nam")
                    .placeId("ChIJVVVR0doudTERCfa7bfewLWs")
                    .isDefault(false)
                    .user(customer1)
                    .build();

            Address address3 = Address.builder()
                    .detail("Quận 2, Hồ Chí Minh, Việt Nam")
                    .placeId("ChIJu9L9H8AldTERVlvuTtaBpW8")
                    .isDefault(true)
                    .user(freelancer1)
                    .build();

            Address address4 = Address.builder()
                    .detail("Hà Nội, Việt Nam")
                    .placeId("ChIJoRyG2ZurNTERqRfKcnt_iOc")
                    .isDefault(true)
                    .user(freelancer2)
                    .build();

            Address address5 = Address.builder()
                    .detail("Quảng Ngãi, Việt Nam")
                    .placeId("ChIJOTlgic1SaDERw0JmWgNPVS4")
                    .isDefault(true)
                    .user(freelancer3)
                    .build();

            addressRepository.save(address1);
            addressRepository.save(address2);
            addressRepository.save(address3);
            addressRepository.save(address4);
            addressRepository.save(address5);

            initBankAccounts(customer1, freelancer1, freelancer2, freelancer3);
            initWorks(customer1, freelancer1, freelancer2, freelancer3, address1);
        }
    }
}
