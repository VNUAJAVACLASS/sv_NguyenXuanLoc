package fita;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private final SessionFactory sessionFactory;

    public SubjectDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            subjects = session.createQuery("FROM Subject", Subject.class).list();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách môn học: " + e.getMessage());
        }
        return subjects;
    }

    public boolean addSubject(Subject subject) {
        if (!Subject.isValidCode(subject.getSubjectCode()) || 
            !Subject.isValidCredit(subject.getCredit()) ||
            !subject.isValidWeights()) {
            System.err.println("Mã môn học, số tín chỉ hoặc trọng số không hợp lệ.");
            return false;
        }
        if (subjectExists(subject.getSubjectCode())) {
            System.err.println("Mã môn học đã tồn tại: " + subject.getSubjectCode());
            return false;
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(subject);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Lỗi khi thêm môn học: " + e.getMessage());
            return false;
        }
    }

    public boolean updateSubject(Subject subject) {
        if (!Subject.isValidCode(subject.getSubjectCode()) || 
            !Subject.isValidCredit(subject.getCredit()) ||
            !subject.isValidWeights()) {
            System.err.println("Mã môn học, số tín chỉ hoặc trọng số không hợp lệ.");
            return false;
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(subject);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Lỗi khi cập nhật môn học: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteSubject(String subjectCode) {
        if (!Subject.isValidCode(subjectCode)) {
            System.err.println("Mã môn học không hợp lệ.");
            return false;
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Subject subject = session.get(Subject.class, subjectCode);
            if (subject != null) {
                session.remove(subject);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Lỗi khi xóa môn học: " + e.getMessage());
            return false;
        }
    }

    public boolean subjectExists(String subjectCode) {
        try (Session session = sessionFactory.openSession()) {
            Subject subject = session.get(Subject.class, subjectCode);
            return subject != null;
        } catch (Exception e) {
            System.err.println("Lỗi khi kiểm tra sự tồn tại của môn học: " + e.getMessage());
            return false;
        }
    }
}