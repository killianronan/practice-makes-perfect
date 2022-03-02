library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
entity shift is
	Port ( 
		in0 : in std_logic;
		in1 : in std_logic;
		in2 : in std_logic;
		sel : in std_logic_vector(1 downto 0);
		z : out std_logic
	);
end shift;

architecture Behavioral of shift is
begin
process (sel,in1,in2)
		begin
		case sel is
		    when "00" => z <= in0 after 2ns;
			when "01" => z <= in1 after 2ns;
			when "10" => z <= in2 after 2ns;
			when others => z <= '0' after 2ns;
		end case;
	end process;

end Behavioral;
