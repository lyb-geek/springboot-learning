package com.github.lybgeek.check.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunicationsUnHealthEvent {

   private SQLException sqlException;


}
