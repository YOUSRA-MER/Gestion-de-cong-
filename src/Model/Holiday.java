package Model;

import java.time.LocalDate;

public class Holiday {
    private int id;
    private LocalDate startDate, endDate;
    private TypeConge conge;
    private int idEmployee;

    public Holiday(int id, int idEmployee, LocalDate startDate, LocalDate endDate, TypeConge conge) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.conge = conge;
        this.idEmployee = idEmployee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TypeConge getConge() {
        return conge;
    }

    public void setConge(TypeConge conge) {
        this.conge = conge;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }
}