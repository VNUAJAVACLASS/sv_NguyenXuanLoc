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

    // Cài đặt phương thức tính điểm môn học cho JavaSubject
    public float calSubjectMark()
    {
        return attendanceMark * 0.1f + midExamMark * 0.3f + finalExamMark * 0.6f;
    }

	
	}
