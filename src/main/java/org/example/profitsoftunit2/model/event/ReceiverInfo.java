package org.example.profitsoftunit2.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for transport receiver data like email and name
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiverInfo {

	private String name;

	private String email;
}
