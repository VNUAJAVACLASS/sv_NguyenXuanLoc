package fita.vnua.edu.vn;

public abstract class Subject implements iCreditSubject
{
	
    protected String subjectCode;
    protected String subjectName;
    protected int credit;
    /*
    protected float attendanceMark;
    protected float midExamMark;
    protected float finalExamMark; */
    
    public Subject() {}

    public Subject(String subjectCode, String subjectName, int credit)
    {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
    }

    // Phương thức tính điểm quy đổi từ thang 10 sang thang 4
    /*
    public float calConversionMark()
    {
        return (attendanceMark * 0.1f + midExamMark * 0.3f + finalExamMark * 0.6f) / 10 * 4;
    }
    */

    // Phương thức trừu tượng để tính điểm môn học (lớp con sẽ cài đặt)
    
    // Phương thức tính điểm chữ (A, B, C, ...)
    public String calGrade()
    {
        float grade = calConversionMark();
        if (grade == 0) return "F";
        if (grade == 1) return "D";
        if (grade == 1.5f) return "D+";
        if (grade == 2) return "C";
        if (grade == 2.5f) return "C+";
        if (grade == 3) return "B";
        if (grade == 3.5f) return "B+";
        if (grade == 4) return "A";
        return " ";
    }
	
    /*
    public void setAttendanceMark(float attendanceMark)
    {
        this.attendanceMark = attendanceMark;
    }

    public void setMidExamMark(float midExamMark)
    {
        this.midExamMark = midExamMark;
    }

    public void setFinalExamMark(float finalExamMark)
    {
        this.finalExamMark = finalExamMark;
    }
	*/
    /*
    @Override
    public String toString()
    {
        return "Subject code: " + subjectCode +
               ", Subject name: " + subjectName +
               ", Credit: " + credit +
               ", Attendance: " + attendanceMark +
               ", Mid Exam: " + midExamMark +
               ", Final Exam: " + finalExamMark +
               ", Final Grade (10-point): " + calSubjectMark() +
               ", Grade (letter): " + calGrade();
             */
    public String getSubjectName() {
    	return subjectName;
    }
    
    public String getSubjectCode() {
    	return subjectCode;
    }
    
    public int getCredit() {
    	return credit;
    }
}
