package dk.jannikognicklas.monikasfrisorsalon.repositories;

import dk.jannikognicklas.monikasfrisorsalon.infrastructure.DbConfig;
import dk.jannikognicklas.monikasfrisorsalon.models.Booking;
import dk.jannikognicklas.monikasfrisorsalon.models.HairTreatment;
import dk.jannikognicklas.monikasfrisorsalon.models.enums.Status;
import dk.jannikognicklas.monikasfrisorsalon.models.enums.Treatments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TreatmentRepository {
    private DbConfig config;

    public TreatmentRepository(DbConfig config) {
        this.config = config;
    }

    public List<HairTreatment> findAllTreatments() {
        List<HairTreatment> treatments = new ArrayList<>();
        String sql = "SELECT * FROM hair_treatments";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                Treatments hairTreatment = Treatments.valueOf(rs.getString("hair_treatment"));
                int duration = rs.getInt("duration");
                double price = rs.getBigDecimal("price").doubleValue();

                treatments.add(new HairTreatment(id, hairTreatment, duration, price));
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to find Booking by employee id " + e);
        }

        return treatments;
    }
}
