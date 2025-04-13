package fita.vnua.edu.vn;

class Subject 
{
	private
		String subjectCode;
		String subjectName;
		int credit;
		float attendanceMark;
		float midExamMark;
		float finalExamMark;
	public Subject()
	{
		
	}
	public Subject(String subjectCode, String subjectName, int credit)
	{
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.credit = credit;
	}
	/*
	  calConversionMark (phương thức tính điểm quy đổi thang 4), calConversionMark(String grade)
	 
	(quy đổi từ điểm thang chữ sang thang 4), calGrade (quy ra điểm thang chữ)
	*/
	public float calConversionMark()
	{
		return (attendanceMark * 0.1f + midExamMark * 0.3f + finalExamMark * 0.6f) / 10 * 4;
	}
	
	public float calConversionMark(String grade)
	{
		String cvm = calGrade();
		float gp = -1;
		if(cvm == "F")
			gp = 0;
		if(cvm == "D")
			gp = 1f;
		if(cvm == "D+")
			gp = 1.5f;
		if(cvm == "C")
			gp = 2;
		if(cvm == "C+")
			gp = 2.5f;
		if(cvm == "B")
			gp = 3;
		if(cvm == "B+")
			gp = 3.5f;
		if(cvm == "A")
			gp = 4;
		return gp;
		
	}
	public String calGrade()
	{
		float grade = calConversionMark();
		if(grade == 0)
			return "F";
		if(grade == 1)
			return "D";
		if(grade == 1.5f)
			return "D+";
		if(grade == 2)
			return "C";
		if(grade == 2.5f)
			return "C+";
		if(grade == 3)
			return "B";
		if(grade == 3.5f)
			return "B+";
		if(grade == 4)
			return "A";
		return " ";
	}
	public float calSubjectMark()
	{
		return attendanceMark * 0.1f + midExamMark * 0.3f + finalExamMark * 0.6f;
		
	}
	public int getCredit()
	{
		return credit;
	}
	public void setattenDanceMark(float attendanceMark)
	{
		this.attendanceMark = attendanceMark; 
	}
	public void setmidExamMark(float midExamMark)
	{
		this.midExamMark = midExamMark; 
	}
	public void setfinalExamMark(float finalExamMark)
	{
		this.finalExamMark = finalExamMark; 
	}
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
	}
}
