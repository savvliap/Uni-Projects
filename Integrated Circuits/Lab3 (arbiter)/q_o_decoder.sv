module q_o_decoder(
input logic [2:0] sig_i,
output logic [6:0] sig_o);

always_block begin 
	case(sig_i)
		3'b000: sig_o=7'b0111111;
		3'b001: sig_o=7'b0000110;
		3'b010: sig_o=7'b1011011;
		3'b011: sig_o=7'b1001111;
		3'b100: sig_o=7'b1100110;
		3'b101: sig_o=7'b1101101;
		3'b110: sig_o=7'b1111101;
		3'b111: sig_o=7'b0000111;
	endcase 
end
