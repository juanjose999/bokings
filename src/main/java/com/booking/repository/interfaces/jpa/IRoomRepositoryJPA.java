package com.booking.repository.interfaces.jpa;

import com.booking.entiti.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoomRepositoryJPA extends JpaRepository<Room,Long> {
}
