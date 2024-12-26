package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.*;

public class EmployeeDAOimplement implements GenericDAOI<Employees> {

    @Override
    public void addElement(Employees emp) {
        String sql = "INSERT INTO employees (nom, prenom, email, salaire, role, poste, telephone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = Connect.getConnection().prepareStatement(sql)) {
            st.setString(1, emp.getNom());
            st.setString(2, emp.getPrenom());
            st.setString(3, emp.getEmail());
            st.setDouble(4, emp.getSalaire());
            st.setString(5, emp.getRole().name());
            st.setString(6, emp.getPoste().name());
            st.setString(7, emp.getTelephone());
            st.executeUpdate();
            System.out.println("Employee added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
        }
    }

    @Override
    public void updateElement(Employees emp) {
        String sqlFirstCase = "UPDATE employees SET nom = ?, prenom = ?, email = ?, salaire = ?, role = ?, poste = ?, telephone = ? WHERE idEmployee = ?";
        String sqlSecondCase = "UPDATE employees SET nom = ?, prenom = ?, email = ?, salaire = ?, role = ?, poste = ?, telephone = ?,solde = ? WHERE idEmployee = ?";
        if(emp.getSolde() == 25) {
            try (PreparedStatement st = Connect.getConnection().prepareStatement(sqlFirstCase)) {
                st.setString(1, emp.getNom());
                st.setString(2, emp.getPrenom());
                st.setString(3, emp.getEmail());
                st.setDouble(4, emp.getSalaire());
                st.setString(5, emp.getRole().name());
                st.setString(6, emp.getPoste().name());
                st.setString(7, emp.getTelephone());
                st.setInt(8, emp.getId());

                int rowsAffected = st.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected);

                if (rowsAffected > 0) {
                    System.out.println("Employee updated successfully! details :"+emp);
                } else {
                    System.err.println("No rows were affected.");
                }
            } catch (SQLException e) {
                System.err.println("Error updating employee: " + e.getMessage());
                e.printStackTrace();
            }
        }
        else {
            try (PreparedStatement st = Connect.getConnection().prepareStatement(sqlSecondCase)) {
                st.setString(1, emp.getNom());
                st.setString(2, emp.getPrenom());
                st.setString(3, emp.getEmail());
                st.setDouble(4, emp.getSalaire());
                st.setString(5, emp.getRole().name());
                st.setString(6, emp.getPoste().name());
                st.setString(7, emp.getTelephone());
                st.setInt(8, emp.getSolde());
                st.setInt(9, emp.getId());

                int rowsAffected = st.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected);

                if (rowsAffected > 0) {
                    System.out.println("Employee updated successfully! details :"+emp);
                } else {
                    System.err.println("No rows were affected.");
                }
            } catch (SQLException e) {
                System.err.println("Error updating employee: " + e.getMessage());
                e.printStackTrace();
            }

        }
    }


    @Override
    public void deleteElement(int id) {
        String sql = "DELETE FROM employees WHERE idEmployee = ?";
        try (PreparedStatement st = Connect.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.err.println("No rows were affected.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
        }
    }

    @Override
    public List<Employees> getAllElements() {
        List<Employees> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (PreparedStatement st = Connect.getConnection().prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                employees.add(new Employees(
                        rs.getInt("idEmployee"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getDouble("salaire"),
                        Role.valueOf(rs.getString("role")),
                        Poste.valueOf(rs.getString("poste"))
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        }
        return employees;
    }

    @Override
    public Employees findElement(int idEmp) {
        String sql = "SELECT * FROM employees WHERE idEmployee = ?";
        try (PreparedStatement st = Connect.getConnection().prepareStatement(sql)) {
            st.setInt(1, idEmp);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Employees(
                            rs.getInt("idEmployee"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("telephone"),
                            rs.getDouble("salaire"),
                            Role.valueOf(rs.getString("role")),
                            Poste.valueOf(rs.getString("poste"))
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding employee: " + e.getMessage());
        }
        return null;
    }
}