package fita;

public class Subject {
    private String subjectCode;
    private String subjectName;
    private int credit;
    private double he1;
    private double he2;
    private double he3;
    private double he4;
    private double he5;

    public Subject(String subjectCode, String subjectName, int credit,
                   double he1, double he2, double he3, double he4, double he5) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
        this.he1 = he1;
        this.he2 = he2;
        this.he3 = he3;
        this.he4 = he4;
        this.he5 = he5;
    }

    public Subject (String subjectCode,String subjectName,int credit)
    {
    	this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
    }
    public String getSubjectCode() { 
    	return subjectCode; 
    }
    public void setSubjectCode(String subjectCode) 
    {
    	this.subjectCode = subjectCode; 
    	}

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }

    public double getHe1() { return he1; }
    public void setHe1(double he1) { this.he1 = he1; }

    public double getHe2() { return he2; }
    public void setHe2(double he2) { this.he2 = he2; }

    public double getHe3() { return he3; }
    public void setHe3(double he3) { this.he3 = he3; }

    public double getHe4() { return he4; }
    public void setHe4(double he4) { this.he4 = he4; }

    public double getHe5() { return he5; }
    public void setHe5(double he5) { this.he5 = he5; }
}
