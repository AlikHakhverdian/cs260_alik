first_name = "Alik"
last_name = "Hakhverdyan"

# 1. Compute a, b, c
a = ord(last_name[0]) - ord('A')
b = ord(last_name[1]) - ord('a')
c = ord(first_name[0]) - ord('A')

# 2. Compute x
if b < 2:
    x = a**2 + (5 * b)**2 + c**2
elif c < 17:
    x = (2 * a)**2 + b**2 + (3 * c)**2
else:
    x = a**2 + (2.5 * b)**2 + c**2

# 3. Compute y
if b < 2:
    y = x**(2/3)
elif c < 17:
    y = 0.037 * x + 8.2
else:
    y = 6.4 * x**0.41

# 4. Compute start
start = int(y / 8 + 0.5) * 8 + 1

print(f"start = {start}")
