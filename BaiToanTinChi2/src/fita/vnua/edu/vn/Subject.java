package fita.vnua.edu.vn;

public abstract class Subject implements iCreditSubject
{
    protected String subjectCode;
    protected String subjectName;
    protected int credit;
    
    //BỎ 3 đầu điểm
   
   
    
    public Subject() {}

    public Subject(String subjectCode, String subjectName, int credit)
    {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
    }

    // Phương thức tính điểm quy đổi từ thang 10 sang thang 4
    //public float calConversionMark();
    
   
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

    public int getCredit()
    {
        return credit;
    }


    @Override
    public String toString()
    {
        return "Subject code: " + subjectCode +
               ", Subject name: " + subjectName +
               ", Credit: " + credit +
               "";
    }

	public float calSubjectMark() {
		// TODO Auto-generated method stub
		return 0;
	}

}
