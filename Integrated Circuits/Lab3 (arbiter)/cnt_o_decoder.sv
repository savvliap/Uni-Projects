module cnt_o_decoder(
input logic [3:0] sig_i,
output logic [6:0] sig_o);

always_block begin 
	case(sig_i)
		4'b0000: sig_o=7'b0111111;
		4'b0001: sig_o=7'b0000110;
		4'b0010: sig_o=7'b1011011;
		4'b0011: sig_o=7'b1001111;
		4'b0100: sig_o=7'b1100110;
		4'b0101: sig_o=7'b1101101;
		4'b0110: sig_o=7'b1111101;
		4'b0111: sig_o=7'b0000111;
        4'b1000: sig_o=7'b1111111;
        4'b1001: sig_o=7'b1101111;
        4'b1010: sig_o=7'b1110111;
        4'b1011: sig_o=7'b1111100;
        4'b1100: sig_o=7'b0111001;
        4'b1101: sig_o=7'b1011110;
        4'b1110: sig_o=7'b1111001;
        4'b1111: sig_o=7'b1110001;
	endcase 
end
