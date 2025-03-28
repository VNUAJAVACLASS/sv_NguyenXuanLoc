package toiyeulaptrinh;

class Subject {
    private String subjectCode;
    private String subjectName;
    private int credit;
    private float attendanceMark, midExamMark, finalExamMark;

    public Subject(String subjectCode, String subjectName, int credit) 
    {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
    }
    public float calConversionMark() {
        return (attendanceMark * 0.1f + midExamMark * 0.3f + finalExamMark * 0.6f);
    }
    public float calConversionMark(float grade) {
        return grade / 10 * 4;
    }
    public String calGrade() {
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

