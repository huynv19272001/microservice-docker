/**
 * @author Trung.Nguyen
 * @date 30-May-2022
 * */
package com.lpb.esb.etax.inquiry.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Configuration
public class SimulatorConfig {
	
	@Value("${env.do}")
    private String envDo;

}
