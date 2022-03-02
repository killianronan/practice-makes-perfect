library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
entity mux2_1bit is
	Port(
		s : in std_logic;
		in1 : in std_logic;
		in2 : in std_logic;
		m_out : out std_logic
	);
end mux2_1bit;
architecture Behavioral of mux2_1bit is
begin
   process (s,in1,in2)
		begin
		case  s is
			when '0' => m_out <= in1;
			when '1' => m_out <= in2;
			when others => m_out <= in1;
		end case;
	end process;
end Behavioral;
