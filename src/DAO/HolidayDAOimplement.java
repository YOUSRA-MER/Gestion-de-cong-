package DAO;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Model.Holiday;
import Model.TypeConge;

public class HolidayDAOimplement implements GenericDAOI<Holiday>, DataImportExport<Holiday> {

    @Override
    public void addElement(Holiday holiday) {
        String sql = "INSERT INTO holidays (idEmployee, startDate, endDate, holidayType) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stm = Connect.getConnection().prepareStatement(sql)) {
            stm.setInt(1, holiday.getIdEmployee());
            stm.setDate(2, Date.valueOf(holiday.getStartDate()));
            stm.setDate(3, Date.valueOf(holiday.getEndDate()));
            stm.setString(4, holiday.getConge().name());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateElement(Holiday holiday) {
        String sql = "UPDATE holidays SET startDate = ?, endDate = ?, holidayType = ?, idEmployee = ? WHERE id = ?";
        try (PreparedStatement stm = Connect.getConnection().prepareStatement(sql)) {
            stm.setDate(1, Date.valueOf(holiday.getStartDate()));
            stm.setDate(2, Date.valueOf(holiday.getEndDate()));
            stm.setString(3, holiday.getConge().name());
            stm.setInt(4, holiday.getIdEmployee());
            stm.setInt(5, holiday.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteElement(int id) {
        String sql = "DELETE FROM holidays WHERE id = ?";
        try (PreparedStatement stm = Connect.getConnection().prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Holiday> getAllElements() {
        String sql = "SELECT * FROM holidays";
        List<Holiday> holidays = new ArrayList<>();
        try (Statement stm = Connect.getConnection().createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                holidays.add(new Holiday(
                        rs.getInt("id"),
                        rs.getInt("idEmployee"),
                        rs.getDate("startDate").toLocalDate(),
                        rs.getDate("endDate").toLocalDate(),
                        TypeConge.valueOf(rs.getString("holidayType"))
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return holidays;
    }

    @Override
    public Holiday findElement(int id) {
        String sql = "SELECT * FROM holidays WHERE id = ?";
        try (PreparedStatement stm = Connect.getConnection().prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return new Holiday(
                            rs.getInt("id"),
                            rs.getInt("idEmployee"),
                            rs.getDate("startDate").toLocalDate(),
                            rs.getDate("endDate").toLocalDate(),
                            TypeConge.valueOf(rs.getString("holidayType"))
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void importData(String filePath) {
        String sql = "INSERT INTO holidays (idEmployee, startDate, endDate, holidayType) VALUES (?, ?, ?, ?)";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PreparedStatement stm = Connect.getConnection().prepareStatement(sql)) {

            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    stm.setInt(1, Integer.parseInt(data[0].trim()));
                    stm.setDate(2, Date.valueOf(data[1].trim()));
                    stm.setDate(3, Date.valueOf(data[2].trim()));
                    stm.setString(4, data[3].trim());
                    stm.executeUpdate();
                }
            }
            System.out.println("Holidays imported successfully!");

        } catch (SQLException | IOException e) {
            System.err.println("Error importing data: " + e.getMessage());
        }
    }

    @Override
    public void exportData(String fileName, List<Holiday> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("idEmployee,startDate,endDate,holidayType");
            writer.newLine();
            for (Holiday holiday : data) {
                String line = String.format("%d,%s,%s,%s",
                        holiday.getIdEmployee(),
                        holiday.getStartDate(),
                        holiday.getEndDate(),
                        holiday.getConge().name()
                );
                writer.write(line);
                writer.newLine();
            }
        }
        System.out.println("Holidays exported successfully!");
    }
}
