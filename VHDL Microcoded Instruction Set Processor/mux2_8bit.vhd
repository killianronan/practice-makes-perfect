

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity mux2_8bit is
    Port ( s : in  STD_LOGIC;
           in1 : in  STD_LOGIC_VECTOR (7 downto 0);
           in2 : in  STD_LOGIC_VECTOR (7 downto 0);
           z : out  STD_LOGIC_VECTOR (7 downto 0));
end mux2_8bit;

architecture Behavioral of mux2_8bit is

begin
 process (s,in1,in2)
		begin
		case  s is
			when '0' => z <= in1;
			when '1' => z <= in2;
			when others => z <= in1;
		end case;
	end process;

end Behavioral;
