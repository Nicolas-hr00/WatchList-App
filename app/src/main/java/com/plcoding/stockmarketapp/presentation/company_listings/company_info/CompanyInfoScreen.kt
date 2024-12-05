
package com.plcoding.stockmarketapp.presentation.company_listings.company_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin.Companion.Center
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.stockmarketapp.data.remote.dto.CompanyInfo
import com.plcoding.stockmarketapp.presentation.company_info.CompanyInfoViewModel
import com.plcoding.stockmarketapp.ui.theme.DarkBlue
import com.plcoding.stockmarketapp.ui.theme.DarkBrown
import com.plcoding.stockmarketapp.ui.theme.DarkYellow
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun CompanyInfoScreen(
    symbol: String,
    viewModel: CompanyInfoViewModel = hiltViewModel ()
) {
    val state = viewModel.state
    if (state.error == null)
    {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(DarkYellow)
                .padding(16.dp)
        ) {
            state.company?.let { company ->
                Text(
                    text = company.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    color = DarkBrown
                )
                Spacer (modifier = Modifier.height(8.dp))
                Text(
                    text = company.symbol,
                    fontStyle = FontStyle.Italic,
                    fontSize = 17.sp,
                    modifier = Modifier.fillMaxWidth(),
                    color = DarkBrown
                )
                Spacer (modifier = Modifier.height(8.dp))
                Divider (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                Spacer (modifier = Modifier.height(8.dp))

                Text(
                    text = "Industry: ${company.industry}",
                    fontSize = 17.sp,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    color = DarkBrown
                )

                Spacer (modifier = Modifier.height(8.dp))
                Text(
                    text = "Country: ${company.country}",
                    fontSize = 17.sp,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    color = DarkBrown
                )
                Spacer (modifier = Modifier.height(8.dp))
                Divider (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                Spacer (modifier = Modifier.height(8.dp))
                Text(
                    text = company.description ?: "No description available",
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    color = DarkBrown
                )
                if (state.stockInfos.isNotEmpty())

                {
                    Spacer (modifier = Modifier.height(16.dp))
                    Text(text = "Market Summary", color = DarkBrown)
                    Spacer(modifier = Modifier.height(32.dp))

                }
            }
        }
    }

    if (state.isLoading || state.error != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.error != null -> Text(
                    text = state.error,
                    color = MaterialTheme.colors.error
                )
            }
        }
    }

}
