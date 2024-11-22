module arbiter (
 input logic[7:0] reqs_i,    // The requests to choose from
 input logic[2:0] lowp_i,
 output logic[2:0] grants_o,  // The winner den einai one hot
 output logic[3:0] cnt_o,     // upologizei to plithos ton requests 
 output logic any_grant_o     // Signals that there is at least one winner
);

always_comb                    //kanw to any grant 
begin
   if (reqs_i != 8'b00000000)  //lew oti an den eisagw 0 requests tote to 
      any_grant_o=1'b1;        //anygrant tha einai 1 thelei kati allo?
   else
      any_grant_o=1'b0;
end

always_comb                    //cnt_o
begin
   logic [3:0] temp;
   temp=3'b000;       //kanw elegxw kathe bit tis eisodou gia to an einai 1 kai an einai tote anevazw mia metavliti temp kai tin ekxwrw epeita sthn cnt_o.
   for(int i=0;i<8;i++) 
   begin 
      if(reqs_i[i]==1)
         temp=temp +1;
      else
         temp=temp;
    cnt_o=temp;
end

end

logic [2:0] pri;        //dimiourgw priority
logic [2:0] counter;    //dimiourgw ena counter
always_comb
begin 
   
   if (lowp_i==3'b111) begin     //sunthiki an to priority einai 7 n ginei 0
      pri=0;
   end else begin 
      pri=lowp_i++; 
   end                           //alliws na sunexisei 
   

   while(counter<3'b111)              //to counter na metraei mexri to 7=reqs
   begin
      if (reqs_i[pri]==1'b1) begin    //an exw 1 stop                     
         break;
      end else begin
         counter++;  
      end         
      if(pri==3'b111) begin         //sunthiki gia prior
         pri=3'b000; 
      end else begin
         pri++;      
      end
    grants_o=pri;
   end
end
endmodule