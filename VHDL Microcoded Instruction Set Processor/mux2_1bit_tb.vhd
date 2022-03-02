library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity mux2_1bit_tb is
end mux2_1bit_tb;

architecture Behavioral of mux2_1bit_tb is
    COMPONENT mux2_1bit
    PORT ( s : in  STD_LOGIC;
           in1 : in  STD_LOGIC;
           in2 : in  STD_LOGIC;
           m_out : out  STD_LOGIC
     );
    END COMPONENT;
    --Inputs
   signal s : std_logic :=  '0';
   signal in1 : std_logic := '0';
   signal in2 : std_logic := '0';
 	--Outputs
   signal m_out : std_logic;
begin
    UUT : mux2_1bit Port Map(
        in1 => in1,
        in2 => in2,
        s => s,
        m_out => m_out
   );
sim_proc: process
   begin		
        in1 <= '1';
		in2 <= '0';
      wait for 5 ns;	
        s <= '0';
      wait for 5 ns;	
		s <= '1';
   end process;
end Behavioral;
