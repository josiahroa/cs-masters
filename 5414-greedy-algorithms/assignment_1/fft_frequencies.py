from numpy.fft import fft, ifft
from numpy import real,imag

import csv
from matplotlib import pyplot as plt

file = open('natural_gas_futures_weekly_all.csv','r')
csv_handle = csv.DictReader(file)

weekly_prices = []
dates = []

for rows in csv_handle:
    dates.append(rows['Date'])
    weekly_prices.append(0.5 * (float(rows['High']) + float(rows['Low'])) )
file.close()

plt.plot(range(len(weekly_prices)), weekly_prices, '-b')
plt.xlabel('Week #')
plt.ylabel('Crude Oil Future Price')

# here we have computed the fft of the weekly_prices
fft_data =  fft(weekly_prices)
N = len(fft_data)
assert(N == len(weekly_prices))
# TODO: first fill in the frequencies. Call this list 
# fft_frequencies -- it must have length N
# it must store the frequencies of each element in the fft_data
# ensure that the frequencies of the second half are negative.
# your code here

fft_frequencies = []
for i in range(N):
    if i <= N // 2:
        f = i / N
    else:
        f = -(N - i) / N
    fft_frequencies.append(f)
assert(N == len(fft_frequencies))

print(fft_frequencies)

# This function will be useful for you. Please go through the code.

def select_all_items_in_freq_range(lo, hi):
    # TODO: go through the fft_data and select only those frequencies in the range lo/hi
    new_fft_data = [] # make sure we have the 0 frequency component
    for (fft_val, fft_freq) in zip(fft_data, fft_frequencies):
        if lo <= fft_freq and fft_freq < hi:
            new_fft_data.append(fft_val)
        elif -hi < fft_freq and fft_freq <= -lo:
            new_fft_data.append(fft_val)
        else:
            new_fft_data.append(0.0)
    filtered_data = ifft(new_fft_data)
    assert all( abs(imag(x)) <= 1E-10 for x in filtered_data)
    return [real(x) for x in filtered_data]

upto_1_year = [] # All signal components with frequency < 1/52
one_year_to_1_quarter = [] # All signal components with frequency between 1/52 (inclusive) and 1/13 weeks (not inclusive)
less_than_1_quarter = [] # All signal components with frequency >= 1/13 

# TODO: Redefine the three lists using the select_all_items function
# your code here

upto_1_year = select_all_items_in_freq_range(0, 1/52)
one_year_to_1_quarter = select_all_items_in_freq_range(1/52, 1/13)
less_than_1_quarter = select_all_items_in_freq_range(1/13, max(fft_frequencies))
