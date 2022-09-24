package com.github.lybgeek.db.check.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Connection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunicationsHealthEvent implements Serializable {

    private Connection conn;

}
