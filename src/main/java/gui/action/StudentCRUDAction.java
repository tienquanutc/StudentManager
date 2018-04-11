package gui.action;

import gui.dialog.DIALOG;
import model.Student;
import gui.model.StudentInfoModel;
import gui.model.StudentTableModel;
import gui.loading.LoadingProgress;
import service.StudentService;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StudentCRUDAction extends AbstractAction {
    public static final String ACTION_COMMAND_INSERT = "INSERT";
    public static final String ACTION_COMMAND_DELETE = "DELETE";
    public static final String ACTION_COMMAND_UPDATE = "UPDATE";
    public static final String ACTION_COMMAND_CANCEL = "CANCEL";

    private StudentService service;
    private StudentTableModel studentListTableModel;
    private StudentInfoModel studentInfoModel;

    public StudentCRUDAction(StudentService service, StudentInfoModel studentInfoModel, StudentTableModel studentListTableModel) {
        this.service = service;
        this.studentInfoModel = studentInfoModel;
        this.studentListTableModel = studentListTableModel;
    }

    public void actionPerformed(ActionEvent e) {
        final String action = e.getActionCommand();
        switch (action) {
            case ACTION_COMMAND_INSERT:
                insert();
                return;
            case ACTION_COMMAND_DELETE:
                delete();
                return;
            case ACTION_COMMAND_UPDATE:
                update();
                return;
            case ACTION_COMMAND_CANCEL:
                cancel();
                return;
            default:

        }
    }

    private void insert() {
        LoadingProgress.run(() -> {
            try {
                Student newStudent = studentInfoModel.getNewStudent();
                service.insertStudent(newStudent);
                studentListTableModel.fireTableRowsInserted(newStudent);
            } catch (NumberFormatException ex) {
                DIALOG.error(null, "Failed to insert because field expected number", "INSERT ERROR");
                return;
            }catch (Exception ex) {
                DIALOG.error(null, "Failed to insert because\n" + ex.getMessage(), "INSERT ERROR");
                return;
            }
            DIALOG.info(null, "Insert successfully", "INSERT");
        });
    }


    private void delete() {
        if (studentInfoModel.getBindingStudent().getId() == null) {
            DIALOG.error(null, "must choose a student to delete", "DELETE ERROR");
            return;
        }
        if (DIALOG.confirm(null, "Are you sure delete", "DELETE", "Yes", "No", true)) {
            LoadingProgress.run(() -> {
                try {
                    service.removeStudent(studentInfoModel.getBindingStudent().getId());
                } catch (Exception ex) {
                    DIALOG.error(null, "Failed to delete because \n" + ex.getMessage(), "DELETE ERROR");
                    return;
                }
                studentListTableModel.removeRow(studentInfoModel.getBindingStudent());
                studentInfoModel.setBindingStudent(null);
                DIALOG.info(null, "Delete successfully", "DELETE");
            });
        }
    }

    private void update() {
        if (studentInfoModel.getBindingStudent().getId() == null) {
            DIALOG.error(null, "must choose a student to updaye", "UPDATE ERROR");
            return;
        }
        LoadingProgress.run(() -> {
            try {
                studentInfoModel.updateStudent();
                service.updateStudent(studentInfoModel.getBindingStudent());
                studentListTableModel.fireTableRowsUpdated(studentInfoModel.getBindingStudent());
            } catch (Exception ex) {
                DIALOG.error(null, "Failed to update because " + ex.getMessage(), "UPDATE ERROR");
                return;
            }
            DIALOG.info(null, "Update successfully", "UPDATE");
        });
    }

    private void validate(Student student) {
    }


    private void cancel() {

    }
}
