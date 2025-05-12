package fita;

public class UserSubject {
    private String userCode;
    private String subjectCode;
    private double score1, score2, score3, score4, score5;

    public UserSubject(String userCode, String subjectCode, double score1, double score2, double score3, double score4, double score5) {
        this.userCode = userCode;
        this.subjectCode = subjectCode;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.score4 = score4;
        this.score5 = score5;
    }

    // Getters and Setters
    public String getUserCode() { return userCode; }
    public void setUserCode(String userCode) { this.userCode = userCode; }
    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    public double getScore1() { return score1; }
    public void setScore1(double score1) { this.score1 = score1; }
    public double getScore2() { return score2; }
    public void setScore2(double score2) { this.score2 = score2; }
    public double getScore3() { return score3; }
    public void setScore3(double score3) { this.score3 = score3; }
    public double getScore4() { return score4; }
    public void setScore4(double score4) { this.score4 = score4; }
    public double getScore5() { return score5; }
    public void setScore5(double score5) { this.score5 = score5; }

    // Validation
    public static boolean isValidScore(double score) {
        return score >= 0 && score <= 10;
    }

    public boolean areScoresValid() {
        return isValidScore(score1) && isValidScore(score2) && isValidScore(score3) &&
               isValidScore(score4) && isValidScore(score5);
    }

    @Override
    public String toString() {
        return String.format("UserSubject{user='%s', subject='%s', scores=[%.1f, %.1f, %.1f, %.1f, %.1f]}",
                userCode, subjectCode, score1, score2, score3, score4, score5);
    }
}