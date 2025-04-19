package fita.vnua.edu.vn;

public abstract class PythonSubject extends Subject 
{
	protected float attendanceMark;
    protected float midExamMark;
    protected float finalExamMark;
    private float assignmentMark; 

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
    public void setAssignmentMark(float assignmentMark)
    {
        this.assignmentMark = assignmentMark;
    }
    
    public PythonSubject(String subjectCode, String subjectName, int credit)
    {
        super(subjectCode, subjectName, credit);
    }

    // Cài đặt phương thức tính điểm môn học cho PythonSubject với 4 đầu điểm
    @Override
    public float calSubjectMark()
    {
        return attendanceMark * 0.1f + midExamMark * 0.2f + finalExamMark * 0.5f + assignmentMark * 0.2f;
    }
    public String toString()
    {
        return "Subject code: " + subjectCode +
               ", Subject name: " + subjectName +
               ", Credit: " + credit +
               ", Attendance: " + attendanceMark +
               ", Mid Exam: " + midExamMark +
               ", AssignmentMark Exam: "+assignmentMark+
               ", Final Exam: " + finalExamMark +
               ", Final Grade (10-point): " + calSubjectMark() +
               ", Grade (letter): " + calGrade();
    }
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

}
