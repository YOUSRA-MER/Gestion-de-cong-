package DAO;
import java.util.List;

import Model.Employees;
public interface GenericDAOI<T> {
    public void addElement(T element);
    public void updateElement(T element);
    public void deleteElement(int id);
    public List<T> getAllElements();
    public T findElement(int id);
}