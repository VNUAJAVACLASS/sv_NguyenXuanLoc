package toiyeulaptrinh;

class Subject {
    private String subjectCode;
    private String subjectName;
    private int credit;
    private float attendanceMark, midExamMark, finalExamMark;
    
    public Subject()
    {
    	
    }
    public Subject(String subjectCode, String subjectName, int credit) 
    {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
    }
    public float calConversionMark() 
    {
        return (attendanceMark * 0.1f + midExamMark * 0.3f + finalExamMark * 0.6f);
    }
    public float calConversionMark(String grade) {
        String diemChu = calGrade();  
        if (diemChu.equals("A")) return 8.5f;
        else if (diemChu.equals("B")) return 7.0f;
        else if (diemChu.equals("C")) return 5.5f;
        else if (diemChu.equals("D")) return 4.0f;
        else return 0.0f;
    }
    public String calGrade() 
    {
        float avg = calConversionMark();
        return avg >= 8.5 ? "A" : avg >= 7 ? "B" : avg >= 5.5 ? "C" : avg >= 4 ? "D" : "F";
    }
    public float calSubjectMark() 
    {
    	return calConversionMark(); 
    }
    public int getCredit() 
    {
    	return credit; 
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
    public String toString() {
        return subjectCode + " - " + subjectName + ", Credit: " + credit;
    }
}

