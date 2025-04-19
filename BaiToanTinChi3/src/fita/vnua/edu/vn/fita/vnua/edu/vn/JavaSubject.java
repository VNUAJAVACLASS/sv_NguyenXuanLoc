package fita.vnua.edu.vn;

public abstract class JavaSubject extends Subject 
{
	protected float attendanceMark;
    protected float midExamMark;
    protected float finalExamMark;
    
    public JavaSubject(String subjectCode, String subjectName, int credit)
    {
        super(subjectCode, subjectName, credit);
    }
    

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
    // Cài đặt phương thức tính điểm môn học cho JavaSubject
    @Override
    public float calSubjectMark()
    {
        return attendanceMark * 0.1f + midExamMark * 0.3f + finalExamMark * 0.6f;
    }
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
    }
   
}
