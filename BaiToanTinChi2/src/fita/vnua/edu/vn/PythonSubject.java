package fita.vnua.edu.vn;

public abstract class PythonSubject extends Subject 
{
    protected  float assignmentMark;
    protected float attendanceMark;
    protected float midExamMark;
    protected float finalExamMark;

    public PythonSubject(String subjectCode, String subjectName, int credit)
    {
        super(subjectCode, subjectName, credit);
    }

    // Cài đặt phương thức tính điểm môn học cho PythonSubject với 4 đầu điểm
    public float calSubjectMark()
    {
        return attendanceMark * 0.1f + midExamMark * 0.2f + finalExamMark * 0.5f + assignmentMark * 0.2f;
    }

    // Set điểm bài tập
    public void setAssignmentMark(float assignmentMark)
    {
        this.assignmentMark = assignmentMark;
    }
}
