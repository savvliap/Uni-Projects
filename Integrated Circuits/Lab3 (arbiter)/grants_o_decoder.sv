module grants_o_decoder(
input logic enable,
input logic [2:0] sig_i,
output logic [7:0] sig_o);


always_block begin
	if (en) begin 
		case(sig_i)
			3'b000: sig_o=8'b00000001;
			3'b001: sig_o=8'b00000010;
			3'b010: sig_o=8'b00000100;
			3'b011: sig_o=8'b00001000;
			3'b100: sig_o=8'b00010000;
			3'b101: sig_o=8'b00100000;
			3'b110: sig_o=8'b01000000;
			3'b111: sig_o=8'b10000000;
		endcase 
	end else begin 
		sig_o=8'b00000000;
	end
end